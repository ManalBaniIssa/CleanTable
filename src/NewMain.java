
public class NewMain {

    public static void main(String[] args) throws Exception {
        CleanTable tbl = new CleanTable("ID", "Name", "Age");
        tbl.addRow("1", "Ayman", "24.5");
        tbl.addRow("2", "Ahmad", "21.3");
        tbl.addRow("3", "Mahmoud", "15");
        tbl.addRow("4", "Raja", "42");
        tbl.print();
    }
    
}
