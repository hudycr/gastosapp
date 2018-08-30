package com.uep.followmymoney.data;

import android.content.Context;

import com.uep.followmymoney.dao.CuentaDao;
import com.uep.followmymoney.dao.DaoSession;
import com.uep.followmymoney.dao.OperacionDao;
import com.uep.followmymoney.domain.Cuenta;
import com.uep.followmymoney.domain.Operacion;
import com.uep.followmymoney.domain.TipoOperacion;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;

/**
 * Created by hchan on 11/04/2018.
 */

public class OperacionDataMgr {

    private static final String TAG = OperacionDataMgr.class.getCanonicalName();

    static Context context;

    private static OperacionDataMgr instance = new OperacionDataMgr();

    public static OperacionDataMgr getInstance(Context paramContext) {
        if (instance == null) {
            instance = new OperacionDataMgr();
        }
        //CuentaDataMgr localCuentaDataMgr = instance;
        context = paramContext;
        return instance;
    }

    public void insert(Operacion entity) {
        DataContext.getInstance(context).openWritableDb().getOperacionDao().insert(entity);
    }

    public void update(Operacion entity){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();
        OperacionDao dao = daoSession.getOperacionDao();
        dao.update(entity);
    }

    public void delete(Operacion entity){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();
        OperacionDao dao = daoSession.getOperacionDao();
        dao.delete(entity);
    }

    public void generarNuevaOperacion(Operacion operacion){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();

        Database db = daoSession.getDatabase();
        db.beginTransaction();

        try {
            CuentaDao cuentaDao = daoSession.getCuentaDao();
            OperacionDao operacionDao = daoSession.getOperacionDao();
            //deposito mas a una cuenta
            if (operacion.getTipo() == TipoOperacion.DEPOSITO) {
                Cuenta cuentaMas = cuentaDao.load(operacion.getCuentaId());
                //cuentaMas.setSaldo(cuentaMas.getSaldo() + operacion.getMonto());
                //operacion.setSaldo(cuentaMas.getSaldo());

                cuentaDao.update(cuentaMas);
                operacionDao.insert(operacion);
            } else  if (operacion.getTipo() == TipoOperacion.RETIRO) {
                //retiro cuenta a efectivo
                QueryBuilder<Cuenta> query = daoSession.getCuentaDao().queryBuilder();
                //query.where(CuentaDao.Properties.EsEfectivo.eq(true));
                Cuenta cuentaEfectivo = query.list().get(0);
                //cuentaEfectivo.setSaldo(cuentaEfectivo.getSaldo() + operacion.getMonto());

                Operacion operacionEfectivo = new Operacion();
                operacionEfectivo.setConcepto("Depósito efectivo");
                operacionEfectivo.setTipo(TipoOperacion.DEPOSITO);
                operacionEfectivo.setFecha(Calendar.getInstance().getTime());
                operacionEfectivo.setMonto(operacion.getMonto());
                operacionEfectivo.setCuenta(cuentaEfectivo);
                operacionEfectivo.setCuentaId(cuentaEfectivo.getId());
                //operacionEfectivo.setSaldo(cuentaEfectivo.getSaldo());

                Cuenta cuentaMenos = cuentaDao.load(operacion.getCuentaId());
                //cuentaMenos.setSaldo(cuentaMenos.getSaldo() - operacion.getMonto());
                //operacion.setSaldo(cuentaMenos.getSaldo());

                cuentaDao.update(cuentaEfectivo);
                cuentaDao.update(cuentaMenos);
                operacionDao.insert(operacion);
            } else if (operacion.getTipo() == TipoOperacion.GASTO){
                //Gasto menos a una cuenta
                Cuenta cuentaMenos = cuentaDao.load(operacion.getCuentaId());
                //cuentaMenos.setSaldo(cuentaMenos.getSaldo() - operacion.getMonto());
                //operacion.setSaldo(cuentaMenos.getSaldo());

                cuentaDao.update(cuentaMenos);
                operacionDao.insert(operacion);
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
        } finally {
            db.endTransaction();
        }
    }
}
