
import java.util.LinkedList;

/**
 * CleanTable
 * [Ported from PHP]
 * @author xdevnull
 */
public class CleanTable {
    
    /**
     * Mask character
     */
    protected final char mask = '+';
    
    /**
     * Divider character
     */
    protected final char divider = '-';
    
    /**
     * Table Header
     */
    protected String[] header;
    
    /**
     * Table Rows
     */
    protected LinkedList<String[]> rows;
    
    /**
     * Table Transposed rows
     */
    protected LinkedList<String[]> transRows;
    
    /**
     * Table highest columns widths
     */
    protected int[] columnWidths;
    
    /**
     * printMask String (used in printf)
     */
    protected String printMask;
    
    /**
     * tableDivider (String as divider between headers and rows)
     */
    protected String tableDivider;
    
    /**
     * CleanTable
     * @param header 
     */
    public CleanTable(String ... header) {
        this.header = new String[header.length];
        for(int i = 0; i < header.length; i++)
            this.header[i] = "   " + header[i] + "   ";
        rows = new LinkedList<String[]>();
        transRows = new LinkedList<String[]>();
    }
    
    /**
     * Print Table
     * @throws Exception 
     */
    public void print() throws Exception {
        if(rows.isEmpty())
                throw new Exception("Not rows available!");
        if(header.length != rows.get(0).length)
            throw new Exception("Header and Rows must be equal!");
        System.out.println(this.getDivider());
        printFormatRow(header);
        System.out.println(this.getDivider());
        for(String[] row : rows)
            printFormatRow(row);
        System.out.println(this.getDivider());
    }
    
    /**
     * Add Row
     * @param row
     * @throws Exception 
     */
    public void addRow(String ... row) throws Exception {
        if(row.length != header.length)
            throw new Exception("Row length doesn't match header length!");
        for(int i = 0; i < row.length; i++)
            row[i] = "   " + row[i] + "   ";
        rows.add(row);
    }
    
    /**
     * transpose Row
     * (Merge header with rows) 
     * ex: [H1, R11, R21, R31]
     * R11 = (Row 1 column 1)
     */
    private void transposeRow() {
        if(!transRows.isEmpty())
            return;
        for(int i = 0; i < header.length; i++) {
            String[] transposed = new String[header.length + 1];
            transposed[0] = header[i];
            int atPosition = 0;
            for(String row : rows.get(i))
                transposed[++atPosition] = row;
            transRows.add(transposed);
        }
    }
    
    /**
     * Get highest columns width 
     * 
     * @return 
     */
    private int[] getColWidths() {
        if(columnWidths != null)
            return columnWidths;
        this.transposeRow();
        columnWidths = new int[header.length];
        for(int i = 0; i < transRows.size(); i++) {
            int max = 0;
            for(String str : transRows.get(i))
                if(str.length() > max)
                    max = str.length();
            columnWidths[i] = max + 3;
        }
        return columnWidths;
    }
    
    /**
     * Get PrintMask
     * @return 
     */
    private String getPrintMask() {
        if(printMask != null)
            return printMask;
        String[] mask = new String[columnWidths.length];
        for(int i = 0; i < mask.length; i++)
            mask[i] = "%" + (i + 1) + "$-" + columnWidths[i] + "s";
        printMask = "|" + String.join("|", mask) + "|";
        return printMask;
    }
    
    /**
     * Format and print row
     * @param row 
     */
    public void printFormatRow(String[] row) {
        System.out.printf(this.getPrintMask(), row);
        System.out.println();
    }
    
    /**
     * Get Table Divider
     * @return 
     */
    public String getDivider() {
        if(tableDivider != null)
            return tableDivider;
        String[] sections = new String[this.getColWidths().length];
        for(int i = 0; i < columnWidths.length; i++)
            sections[i] = new String(new char[columnWidths[i]]).replace("\0", "" + divider);
        tableDivider = mask + String.join("" + mask, sections) + mask;
        return tableDivider;
    }
    
    
    
    
}
