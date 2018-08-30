package com.uep.followmymoney.data;

import android.content.Context;
import android.util.Log;

import com.uep.followmymoney.dao.BlockDao;
import com.uep.followmymoney.dao.CuentaDao;
import com.uep.followmymoney.dao.DaoSession;
import com.uep.followmymoney.dao.TransactionDao;
import com.uep.followmymoney.dao.TransactionOutputDao;
import com.uep.followmymoney.domain.Block;
import com.uep.followmymoney.domain.Cuenta;
import com.uep.followmymoney.domain.Operacion;
import com.uep.followmymoney.domain.TipoCuenta;
import com.uep.followmymoney.domain.TipoCuentaConverter;
import com.uep.followmymoney.domain.TipoOperacion;
import com.uep.followmymoney.domain.Transaction;
import com.uep.followmymoney.domain.TransactionOutput;
import com.uep.followmymoney.utils.StringUtil;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hchan on 10/04/2018.
 */

public class CuentaDataMgr {

    private static final String TAG = CuentaDataMgr.class.getCanonicalName();

    static Context context;

    private static CuentaDataMgr instance = new CuentaDataMgr();

    public static CuentaDataMgr getInstance(Context paramContext) {
        if (instance == null) {
            instance = new CuentaDataMgr();
        }
        //CuentaDataMgr localCuentaDataMgr = instance;
        context = paramContext;
        return instance;
    }

    public List<Cuenta> getAll() {
        DaoSession daoSession = DataContext.getInstance(context).openReadableDb();
        QueryBuilder<Cuenta> query = daoSession.getCuentaDao().queryBuilder();

        query.where(query.or(CuentaDao.Properties.Tipo.eq(0),CuentaDao.Properties.Tipo.eq(1)));
        query.orderAsc(CuentaDao.Properties.Id);
        return query.list();
    }

    public List<Cuenta> getAll(boolean esEfectivo) {
        DaoSession daoSession = DataContext.getInstance(context).openReadableDb();
        QueryBuilder<Cuenta> query = daoSession.getCuentaDao().queryBuilder();
        if (esEfectivo)
            query.where(CuentaDao.Properties.Tipo.eq(1));
        else
            query.where(CuentaDao.Properties.Tipo.eq(0));
        query.orderAsc(CuentaDao.Properties.Id);
        return query.list();
    }

    public Cuenta getCuentaByTipo(TipoCuenta tipo) {
        int tipoValue = new TipoCuentaConverter().convertToDatabaseValue(tipo);
        DaoSession daoSession = DataContext.getInstance(context).openReadableDb();
        QueryBuilder<Cuenta> query = daoSession.getCuentaDao().queryBuilder();
        query.where(CuentaDao.Properties.Tipo.eq(tipoValue));
        query.orderAsc(CuentaDao.Properties.Id);
        if (query.list().size() > 0)
            return query.list().get(0);
        return null;
    }

    public Cuenta getById(long id) {
        DaoSession daoSession = DataContext.getInstance(context).openReadableDb();
        QueryBuilder<Cuenta> query = daoSession.getCuentaDao().queryBuilder();
        query.where(CuentaDao.Properties.Id.eq(id));
        List<Cuenta> cuentas = query.list();
        if (cuentas.size() ==  1)
            return  cuentas.get(0);
        return null;
    }

    public void insert(Cuenta entity) {
        DataContext.getInstance(context).openWritableDb().getCuentaDao().insert(entity);
    }

    public void update(Cuenta entity){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();
        CuentaDao dao = daoSession.getCuentaDao();
        dao.update(entity);
    }

    public void delete(Cuenta entity){
        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();
        CuentaDao dao = daoSession.getCuentaDao();
        dao.delete(entity);
    }

    public void generarNuevaOperacion(Operacion operacion) throws Exception {
        sendFunds(operacion.getCuentaId(), operacion.getTipo(), operacion.getMonto(), operacion.getConcepto(), operacion.getFecha());
    }

    public void sendFunds(long accountId, TipoOperacion tipo, float value, String description, Date date) throws Exception {
        Cuenta sender = null;
        Cuenta recipient = null;
        List<TransactionOutput> inputs = new ArrayList<>();
        float letfOver = 0;
        if (value <=  0) throw new Exception("El valor de transferencia debe ser mayor a cero");
        if (tipo == TipoOperacion.DEPOSITO) {
            sender = getCuentaByTipo(TipoCuenta.ENTRADAS);
            recipient = getById(accountId);
            letfOver = value;
        } else if (tipo == TipoOperacion.GASTO){
            if (getBalance(accountId) < value) throw new Exception("La cuenta no tiene suficientes fondos para realizar el gasto");
            sender = getById(accountId);
            recipient = getCuentaByTipo(TipoCuenta.SALIDAS);

        } else  if (tipo == TipoOperacion.RETIRO) {
            if (getBalance(accountId) < value) throw new Exception("La cuenta no tiene suficientes fondos para realizar el gasto");
            sender = getById(accountId);
            recipient = getCuentaByTipo(TipoCuenta.EFECTIVO);
        }

        if (sender == null) throw new Exception("No se encontró la cuenta emisora");
        if (recipient == null) throw new Exception("No se encontró la cuenta receptora");

        long recipientId = recipient.getId();
        long senderId = sender.getId();

        if (tipo != TipoOperacion.DEPOSITO){
            List<TransactionOutput> funds = TransactionOutputDataMgr.getInstance(context).getByCuentaId(sender.getId());

            float total = 0;
            for (TransactionOutput tout : funds ) {
                total += tout.getValue();
                inputs.add(tout);
                if(total > value) break;
            }
            letfOver = total - value;
        }


        //transaccion padre
        long timeStamp = new Date().getTime();
        Transaction newTransaction =  new Transaction();
        newTransaction.setCuentaReciepientId(recipientId);
        newTransaction.setCuentaSenderId(senderId);
        newTransaction.setValue(value);
        newTransaction.setTipo(tipo);
        newTransaction.setTimeStamp(timeStamp);


        TransactionOutput outputRecipient = new TransactionOutput();
        outputRecipient.setActive(true);
        outputRecipient.setReciepientId(recipientId);
        outputRecipient.setValue(value);
        outputRecipient.setTimeStamp(timeStamp);
        outputRecipient.setDescription(description);
        outputRecipient.setDateTransaction(date);

        TransactionOutput outputSender = new TransactionOutput();
        outputSender.setActive(true);
        outputSender.setReciepientId(senderId);
        outputSender.setValue(letfOver);
        outputSender.setTimeStamp(timeStamp);
        outputSender.setDescription(description);
        outputSender.setDateTransaction(date);

        Block block = new Block();
        block.setTimeStamp(timeStamp);
        //lastblock
        Block lastBlock = BlockDataMgr.getInstance(context).getLastBlock();
        if (lastBlock != null)
            block.setPreviousHash(lastBlock.getHash());
        else
            block.setPreviousHash("");

        DaoSession daoSession = DataContext.getInstance(context).openWritableDb();

        Database db = daoSession.getDatabase();
        db.beginTransaction();

        try {
            BlockDao blockDao = daoSession.getBlockDao();
            TransactionDao transactionDao = daoSession.getTransactionDao();
            TransactionOutputDao transactionOutputDao = daoSession.getTransactionOutputDao();
            //insertar block
            block.calculateHash();
            long blockId = blockDao.insert(block);

            newTransaction.setBlockId(blockId);
            newTransaction.calulateHash();
            long transactionId = transactionDao.insert(newTransaction);

            outputRecipient.setParentTransactionId(transactionId);
            outputSender.setParentTransactionId(transactionId);
            transactionOutputDao.insert(outputRecipient);
            transactionOutputDao.insert(outputSender);
            //eliminar logica los input
            for (TransactionOutput tinput : inputs ) {
                TransactionOutput transBaja = transactionOutputDao.load(tinput.getId());
                transBaja.setActive(false);
                transactionOutputDao.update(transBaja);
            }
            db.setTransactionSuccessful();
        } catch (Exception ex){
                ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public float getBalance(long senderId){
        float total = 0;
        //consultar transaction output de la cuenta
        List<TransactionOutput> trasns = TransactionOutputDataMgr.getInstance(context).getByCuentaId(senderId);
        for (TransactionOutput tout : trasns ) {
            total += tout.getValue();
        }
        return total;
    }

}
