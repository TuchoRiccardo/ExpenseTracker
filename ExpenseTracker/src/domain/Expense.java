package domain;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author carlos Riccardo
 */
public class Expense {
    
    private static int idContador=1;
    
    private int id;
    private String descripcion;
    private float monto;
    private LocalDate date;
    private Categoria categoria; //Enum (Comida,transporte,etc)
    
    
    //Constructor
    public Expense(String descripcion, float monto, LocalDate date, Categoria categoria){
    this.id=idContador++;
    this.descripcion=descripcion;
    this.monto=monto;
    this.date=date;
    this.categoria=categoria;
    }
    
    //Constructor con ID(sirve por si se cargar un archivo, en caso de ser necesario)
    
    public Expense(int id,String descripcion, float monto, LocalDate date, Categoria categoria){
    
    this.id=id;
    this.descripcion=descripcion;
    this.monto=monto;
    this.date=date;
    this.categoria=categoria;
    
    
    
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getMonto() {
        return monto;
    }

    public LocalDate getDate() {
        return date;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    
    //Exportar a CSV
    
    public String aCSV(){
    
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        return id+", "+descripcion+", "+monto+", "+date.format(formatter)+", "+categoria;
        
    
    
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Expense{");
        sb.append("id=").append(id);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", monto=").append(monto);
        sb.append(", date=").append(date);
        sb.append(", categoria=").append(categoria);
        sb.append('}');
        return sb.toString();
    }
    
    
    
    
    
}

