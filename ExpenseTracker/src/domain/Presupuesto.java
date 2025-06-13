package domain;
import java.time.YearMonth;
/**
 *
 * @author carlo
 */
//Clase para establecer un monto fijo en mes elejido y mostrar alerta
public class Presupuesto {
    private YearMonth mes; //Ej. Junio 2025
    private float monto; //Monto limite del presupuesto
    
    public Presupuesto(YearMonth mes, float monto){
    this.mes=mes;
    this.monto=monto;
    
    }

    public YearMonth getMes() {
        return mes;
    }

    public void setMes(YearMonth mes) {
        this.mes = mes;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
    
    
    //Metodo para verificar si un total excede al presupuesto
    public boolean seExcede(float totalGasto){
    return totalGasto > monto;
    
    }
    
    //Metodo para advertir que se esta quedando sin margen
    public boolean limiteCerca(float totalGasto){
    
    return totalGasto >= monto * 0.9 && totalGasto <= monto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Presupuesto{");
        sb.append("mes=").append(mes);
        sb.append(", monto=").append(monto);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
