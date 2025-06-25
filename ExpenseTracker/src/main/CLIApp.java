package main;
import domain.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.*;
public class CLIApp {
    private static final Scanner sc=new Scanner(System.in);
    private static final ExpenseManager manager=new ExpenseManager();
    
    public static void main(String[] args) {
        manager.chequeoAutomaticoAlertas();//Mostrar al inicio
        
        boolean runeando=true;
        
        while(runeando){
        printMenu();
            System.out.println("> ");
            String eleccion=sc.nextLine();
            switch(eleccion){
            case "1" -> addExpense();
                case "2" -> viewExpenses();
                case "3" -> updateExpense();
                case "4" -> deleteExpense();
                case "5" -> viewExpensesByMonth();
                case "6" -> setBudget();
                case "7"-> VerGastosPorCategoria(sc,manager);
                case "8" -> exportCSV();
                case "0" -> runeando = false;
                default -> System.out.println("Opción no válida.");
            
            
            }
        
        }
        
    }
    private static void printMenu() {
        System.out.println("\n===== Control de Gastos =====");
        System.out.println("1. Agregar gasto");
        System.out.println("2. Ver todos los gastos");
        System.out.println("3. Editar gasto");
        System.out.println("4. Eliminar gasto");
        System.out.println("5. Ver gastos por mes");
        System.out.println("6. Establecer presupuesto mensual");
        System.out.println("7. Ver gastos por categoria");
        System.out.println("8. Exportar gastos a CSV");
        System.out.println("0. Salir");
    }
    
    private static void addExpense() {
        System.out.print("Descripción: ");
        String desc = sc.nextLine();

        System.out.print("Monto: ");
        float amount = Float.parseFloat(sc.nextLine());

        System.out.print("Fecha (YYYY-MM-DD): ");
        String fechaTexto=sc.nextLine();
        LocalDate fecha;
        try{
            fecha=LocalDate.parse(fechaTexto);
            
        }catch(DateTimeParseException e){
            System.out.println("Formato de fechaI incorrecto. Por favor use YYYY-MM-DD(Ej. 2025-05-06)");
            return;
        }

        System.out.print("Categoría (FOOD, TRANSPORT, HEALTH, ENTERTAINMENT, UTILITIES, OTHER): ");
        Categoria cat = Categoria.valueOf(sc.nextLine().toUpperCase());

        manager.agregarGasto(desc, amount, fecha, cat);
        System.out.println("\u001B[32m✔ Gasto agregado.\u001B[0m");
    }

     private static void viewExpenses() {
        List<Expense> expenses = manager.getAllExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No hay gastos registrados.");
        } else {
            expenses.forEach(System.out::println);
        }
    }
     
      private static void updateExpense() {
        System.out.print("ID del gasto a editar: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Nueva descripción: ");
        String desc = sc.nextLine();

        System.out.print("Nuevo monto: ");
        float amount = Float.parseFloat(sc.nextLine());

        System.out.print("Nueva fecha (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        System.out.print("Nueva categoría: ");
        Categoria cat = Categoria.valueOf(sc.nextLine().toUpperCase());

        if (manager.actualizarGasto(id, desc, amount, date, cat)) {
            System.out.println("\u001B[32m✔ Gasto actualizado.\u001B[0m");
        } else {
            System.out.println("\u001B[31m✖ Gasto no encontrado.\u001B[0m");
        }
    }
      
      private static void deleteExpense() {
        System.out.print("ID del gasto a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());
        if (manager.eliminarGasto(id)) {
            System.out.println("\u001B[32m✔ Gasto eliminado.\u001B[0m");
        } else {
            System.out.println("\u001B[31m✖ Gasto no encontrado.\u001B[0m");
        }
    }
      
      private static void viewExpensesByMonth() {
        System.out.print("Mes y año (YYYY-MM): ");
        YearMonth ym = YearMonth.parse(sc.nextLine());
        List<Expense> filtered = manager.getExpenseByMonth(ym);
        if (filtered.isEmpty()) {
            System.out.println("No hay gastos para ese mes.");
        } else {
            filtered.forEach(System.out::println);
            float total = manager.traerTotalGastadoEnUnMes(ym);
            System.out.println("Total: $" + total);

            if (manager.sePasoElPresupuesto(ym)) {
                System.out.println("\u001B[31m❌ Presupuesto excedido.\u001B[0m");
            } else if (manager.cercaDelLimitePresupuesto(ym)) {
                System.out.println("\u001B[33m⚠️ Cerca del límite presupuestario.\u001B[0m");
            }
        }
    }
      
      private static void setBudget() {
        System.out.print("Mes y año (YYYY-MM): ");
        YearMonth ym = YearMonth.parse(sc.nextLine());

        System.out.print("Monto del presupuesto: ");
        float amount = Float.parseFloat(sc.nextLine());

        manager.setPresupuesto(ym, amount);
        System.out.println("\u001B[32m✔ Presupuesto establecido.\u001B[0m");
    }
      
      
      private static void exportCSV() {
        List<String> lines = manager.exportarGastosACSV();
        try (FileWriter writer = new FileWriter("gastos.csv")) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
            System.out.println("\u001B[32m✔ Exportado a gastos.csv\u001B[0m");
        } catch (IOException e) {
            System.out.println("\u001B[31m✖ Error al exportar el archivo.\u001B[0m");
        }
    }
    
      private static void VerGastosPorCategoria(Scanner sc, ExpenseManager manager){
          System.out.println("Ingrese la categoria por favor: ");
          
          String nombreCategoria=sc.nextLine().trim();
          
          //Convertir el string en un objeto de la clase categoria
          
          Categoria categoria;
          
          try{
          categoria=Categoria.valueOf(nombreCategoria);
          }catch(IllegalArgumentException e){
              System.out.println("X categoria invalida");
              return;
          }
          
          List<Expense> filtrados=manager.getExpensesByCategoria(categoria);
          
          if(filtrados.isEmpty()){
              System.out.println("No hay gastos registrados en la categoria "+categoria+" .");
          }else{
          System.out.println("📂 Gastos en la categoría '" + categoria + "':");
        for (Expense e : filtrados) {
            System.out.printf("- %s | %.2f | %s\n", e.getDescripcion(), e.getMonto(), e.getDate());
        }
          
          }
      
      
      
      }
    
    
    
}
