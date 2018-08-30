package generator;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by hchan on 09/04/2018.
 */

public class GeneratorMain {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1001, "com.uep.followmymoney.domain");
        schema.setDefaultJavaPackageDao("com.uep.followmymoney.dao");
        addCuenta(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addCuenta(Schema schema) {
        /*Entity historico = schema.addEntity("HistoricoMovimiento");
        historico.addIdProperty();
        historico.addStringProperty("concepto").notNull();
        historico.addDateProperty("fechaRegistro");
        historico.addFloatProperty( "retiro");
        historico.addFloatProperty("deposito");
        historico.addFloatProperty("saldo");

        Property customerId = historico.addLongProperty("cuentaId").notNull().getProperty();
        historico.addToOne(account, customerId);*/

        Entity account = schema.addEntity("Cuenta");
        account.addIdProperty();
        account.addStringProperty("nombre").notNull();
        account.addStringProperty("descripcion");
        account.addDateProperty("fechaCreacion");
        account.addIntProperty("tipo").customType("com.uep.followmymoney.domain.TipoCuenta","com.uep.followmymoney.domain.TipoCuentaConverter");


        Entity operacion = schema.addEntity("Operacion");
        operacion.addIdProperty();
        operacion.addStringProperty("concepto").notNull();
        operacion.addDateProperty("fecha");
        operacion.addFloatProperty( "monto");
        operacion.addFloatProperty("saldo");
        operacion.addIntProperty("tipo").customType("com.uep.followmymoney.domain.TipoOperacion","com.uep.followmymoney.domain.TipoOperacionConverter");

        Property cuentaId = operacion.addLongProperty("cuentaId").notNull().getProperty();
        operacion.addToOne(account, cuentaId).setName("Cuenta");


        Entity transaction = schema.addEntity("Transaction");
        transaction.addIdProperty();
        transaction.addFloatProperty("value").notNull();
        transaction.addStringProperty("signature");
        transaction.addLongProperty("timeStamp").notNull();
        transaction.addIntProperty("tipo").customType("com.uep.followmymoney.domain.TipoOperacion","com.uep.followmymoney.domain.TipoOperacionConverter");

        Property blockId = transaction.addLongProperty("blockId").notNull().getProperty();

        Property cuentaTransId = transaction.addLongProperty("cuentaSenderId").notNull().getProperty();
        transaction.addToOne(account, cuentaTransId).setName("sender");

        Property cuentaRecipId = transaction.addLongProperty("cuentaReciepientId").notNull().getProperty();
        transaction.addToOne(account, cuentaRecipId).setName("reciepient");

        Entity block = schema.addEntity("Block");
        block.addIdProperty();
        block.addStringProperty("hash").notNull();
        block.addStringProperty("previousHash").notNull();
        block.addLongProperty("timeStamp").notNull();
        block.addToMany(transaction, blockId).setName("transactions");

        Entity transactionOutput = schema.addEntity("TransactionOutput");
        transactionOutput.addIdProperty();
        transactionOutput.addFloatProperty("value").notNull();
        transactionOutput.addBooleanProperty("active").notNull();
        transactionOutput.addLongProperty("timeStamp").notNull();
        transactionOutput.addStringProperty("description").notNull();
        transactionOutput.addDateProperty("dateTransaction");

        Property cuentaTransOutRecipId = transactionOutput.addLongProperty("reciepientId").notNull().getProperty();
        transactionOutput.addToOne(account, cuentaTransOutRecipId).setName("reciepient");

        Property parentTransactionId = transactionOutput.addLongProperty("parentTransactionId").notNull().getProperty();
        transaction.addToMany(transactionOutput, parentTransactionId).setName("outputs");
    }

    private static void addHistorico(Schema schema) {

    }
}
