package com.uep.followmymoney.domain;

import org.greenrobot.greendao.annotation.*;

import com.uep.followmymoney.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

import com.uep.followmymoney.dao.CuentaDao;
import com.uep.followmymoney.dao.OperacionDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "OPERACION".
 */
@Entity(active = true)
public class Operacion {

    @Id
    private Long id;

    @NotNull
    private String concepto;
    private java.util.Date fecha;
    private Float monto;
    private Float saldo;

    @Convert(converter = com.uep.followmymoney.domain.TipoOperacionConverter.class, columnType = Integer.class)
    private TipoOperacion tipo;
    private long cuentaId;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 993620003)
    private transient OperacionDao myDao;

    @ToOne(joinProperty = "cuentaId")
    private Cuenta Cuenta;

    @Generated(hash = 236973101)
    private transient Long Cuenta__resolvedKey;

    @Generated(hash = 635301953)
    public Operacion() {
    }

    public Operacion(Long id) {
        this.id = id;
    }

    @Generated(hash = 1462969403)
    public Operacion(Long id, @NotNull String concepto, java.util.Date fecha, Float monto, Float saldo, TipoOperacion tipo,
            long cuentaId) {
        this.id = id;
        this.concepto = concepto;
        this.fecha = fecha;
        this.monto = monto;
        this.saldo = saldo;
        this.tipo = tipo;
        this.cuentaId = cuentaId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 719853811)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOperacionDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getConcepto() {
        return concepto;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setConcepto(@NotNull String concepto) {
        this.concepto = concepto;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public TipoOperacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoOperacion tipo) {
        this.tipo = tipo;
    }

    public long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(long cuentaId) {
        this.cuentaId = cuentaId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1708010095)
    public Cuenta getCuenta() {
        long __key = this.cuentaId;
        if (Cuenta__resolvedKey == null || !Cuenta__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CuentaDao targetDao = daoSession.getCuentaDao();
            Cuenta CuentaNew = targetDao.load(__key);
            synchronized (this) {
                Cuenta = CuentaNew;
                Cuenta__resolvedKey = __key;
            }
        }
        return Cuenta;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 593447571)
    public void setCuenta(@NotNull Cuenta Cuenta) {
        if (Cuenta == null) {
            throw new DaoException("To-one property 'cuentaId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.Cuenta = Cuenta;
            cuentaId = Cuenta.getId();
            Cuenta__resolvedKey = cuentaId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

}
