package domain;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author carlos riccardo
 */
public class ExpenseManager {
    private List <Expense> gastos;
    private Map<YearMonth, Presupuesto> presupuestos;
    
    public ExpenseManager(){
    this.gastos=new ArrayList<>();
    this.presupuestos= new HashMap<>();
    }
    
    //Metodos CRUD
    //Agregar gasto
    
    public void agregarGasto(String descripcion,float monto,LocalDate date,Categoria categoria){
    Expense expense=new Expense(descripcion,monto,date,categoria);
    gastos.add(expense);
    }
    
    //Actualizar gasto por ID
    
    public boolean actualizarGasto(int id, String nuevaDescripcion,float nuevoMonto,LocalDate nuevaFecha,Categoria categoria){
    for(Expense g:gastos){
    if(g.getId()==id){
    g.setDescripcion(nuevaDescripcion);
    g.setMonto(nuevoMonto);
    g.setDate(nuevaFecha);
    g.setCategoria(categoria);
    return true;
    }
    }
    return false;
    }
    
    //Eliminar un gasto por ID
    
    public boolean eliminarGasto(int id){
    return gastos.removeIf(g -> g.getId()==id);
    }
    
    //Obtener todos los datos de los gastos
    
    public List<Expense>getAllExpenses(){
    return new ArrayList<>(gastos);
    }

//Filtrar por Mes

    public List<Expense>getExpenseByMonth(YearMonth mes){
    return gastos.stream()
            .filter(g->YearMonth.from(g.getDate()).equals(mes))
            .collect(Collectors.toList());
    }
    
    //Traer los gastos segun categoria
    
    public List<Expense>getExpensesByCategoria(Categoria categoria){
    return gastos.stream()
            .filter(g->g.getCategoria()==categoria)
            .collect(Collectors.toList());
    }
    
    //Establecer un presupuesto para el mes
    
    public void setPresupuesto(YearMonth mes, float monto){
    presupuestos.put(mes, new Presupuesto(mes,monto));
    }
    
    //Obtener presupuesto de un mes
    public Presupuesto getPresupuesto(YearMonth mes){
    return presupuestos.get(mes);
    }
    
    //obtener total de gastos de un mes
    public float traerTotalGastadoEnUnMes(YearMonth mes){
    return getExpenseByMonth(mes).stream()
            .map(Expense::getMonto)
            .reduce(0f,Float::sum);
    }
    
    //Verificar si se excedio el presupuesto del mes
    
    public boolean sePasoElPresupuesto(YearMonth mes){
    Presupuesto presupuesto=presupuestos.get(mes);
    if(presupuesto ==null) return false;
    return presupuesto.seExcede(traerTotalGastadoEnUnMes(mes));
    }
    
    //Verificar si se está cerca del limite
    public boolean cercaDelLimitePresupuesto(YearMonth mes){
    Presupuesto presupuesto=presupuestos.get(mes);
    if(presupuesto == null) return false;
    return presupuesto.limiteCerca(traerTotalGastadoEnUnMes(mes));
    
    }
    
    //Exportar gastos a CSV
    public List<String> exportarGastosACSV(){
    List<String>lineas = new ArrayList<>();
    lineas.add("ID,Descripcion,Monto,Fecha,Categoria");
    for(Expense g:gastos){
    lineas.add(g.aCSV());
    }
    return lineas;
    }
    
    //Chequeo automatico de alertas
    public void chequeoAutomaticoAlertas(){
    YearMonth mesActual=YearMonth.now();
    if(sePasoElPresupuesto(mesActual)){
        System.out.println("\u001B[31m Te has sobrepasado de tu presupuesto para "+mesActual+"\u001B[0m");
    
    }else if(cercaDelLimitePresupuesto(mesActual)){
        System.out.println("\u001B^[33m Estas cercar del limite del presupuesto para"+mesActual+"\u001B[0m");
    }
    
    }
    
}
