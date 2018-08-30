package com.uep.followmymoney.domain;

import org.greenrobot.greendao.annotation.*;

import com.uep.followmymoney.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

import com.uep.followmymoney.dao.CuentaDao;
import com.uep.followmymoney.dao.TransactionOutputDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "TRANSACTION_OUTPUT".
 */
@Entity(active = true)
public class TransactionOutput {

    @Id
    private Long id;
    private float value;
    private boolean active;
    private long timeStamp;

    @NotNull
    private String description;
    private java.util.Date dateTransaction;
    private long reciepientId;
    private long parentTransactionId;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1473291187)
    private transient TransactionOutputDao myDao;
    @ToOne(joinProperty = "reciepientId")
    private Cuenta reciepient;

    @Generated(hash = 659750442)
    private transient Long reciepient__resolvedKey;

    @Generated(hash = 2109901790)
    public TransactionOutput() {
    }

    public TransactionOutput(Long id) {
        this.id = id;
    }

    @Generated(hash = 519777543)
    public TransactionOutput(Long id, float value, boolean active, long timeStamp, @NotNull String description, java.util.Date dateTransaction, long reciepientId,
            long parentTransactionId) {
        this.id = id;
        this.value = value;
        this.active = active;
        this.timeStamp = timeStamp;
        this.description = description;
        this.dateTransaction = dateTransaction;
        this.reciepientId = reciepientId;
        this.parentTransactionId = parentTransactionId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1065123371)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTransactionOutputDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public java.util.Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(java.util.Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public long getReciepientId() {
        return reciepientId;
    }

    public void setReciepientId(long reciepientId) {
        this.reciepientId = reciepientId;
    }

    public long getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(long parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 110172057)
    public Cuenta getReciepient() {
        long __key = this.reciepientId;
        if (reciepient__resolvedKey == null || !reciepient__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CuentaDao targetDao = daoSession.getCuentaDao();
            Cuenta reciepientNew = targetDao.load(__key);
            synchronized (this) {
                reciepient = reciepientNew;
                reciepient__resolvedKey = __key;
            }
        }
        return reciepient;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 159794190)
    public void setReciepient(@NotNull Cuenta reciepient) {
        if (reciepient == null) {
            throw new DaoException("To-one property 'reciepientId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.reciepient = reciepient;
            reciepientId = reciepient.getId();
            reciepient__resolvedKey = reciepientId;
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
