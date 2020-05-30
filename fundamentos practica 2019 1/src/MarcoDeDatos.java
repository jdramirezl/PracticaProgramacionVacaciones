import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MarcoDeDatos {
    static ArrayList<Dato> datos = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        MarcoDeDatos dato = new MarcoDeDatos();
        Scanner input = new Scanner(System.in);
        boolean salir = false;
        System.out.println("Bienvenido al programa");
        do{
            System.out.print("Que quiere realizar?\n1.Leer archivo - 2. Filtrar - 3.Estadisticas - 4.Guardar datos - 5. salir \nOpcion: ");
            int opcion = input.nextInt();

            switch (opcion) {
                case 1 -> dato.leerDatos();
                case 2 -> {
                    System.out.print("Filtrar por mayores que(1) o menores que(2)\nOpcion(Numero): ");
                    int filtrar = input.nextInt();
                    if (filtrar == 1) {
                        dato.mayoresQue(dato.cualDato());
                    } else if (filtrar == 2) {
                        dato.menoresQue(dato.cualDato());
                    } else {
                        System.out.println("Opcion no valida");
                    }
                }
                case 3 -> {
                    System.out.print("Que dato: moda(1) o conteo(2)\nOpcion(Numero): ");
                    int estadistica = input.nextInt();
                    if (estadistica == 1) {
                        System.out.println("La moda es: " + dato.moda(dato.cualDato()));

                    } else if (estadistica == 2) {
                        System.out.println("Hay " + dato.conteo() + " datos.");
                    } else {
                        System.out.println("Opcion no valida");
                    }
                }
                case 4 -> dato.guardarDatos(datos);
                case 5 -> {
                    System.out.println("Adios");
                    salir = true;
                }
                default -> System.out.println("Opcion no disponible");
            }

        }while (!salir);

    }

    public void leerDatos(){
        Scanner inputfile = null, inputpath;
        inputpath = new Scanner(System.in);

        System.out.print("Ingrese el nombre del archivo a leer \nArchivo: ");
        String nombre = inputpath.nextLine();
        String path = nombre+".csv";

        System.out.println(path);

        try {
            inputfile = new Scanner(new File(path));
        } catch (IOException e){
            System.out.println("Archivo no encontrado");
        }


        inputfile.nextLine(); //Saltar primera linea de texto con los encabezados

        while(inputfile.hasNextLine()){
            //"USW00012839","MIAMI INTERNATIONAL AIRPORT, FL US","1948-01-09","0.0",,"26.1","12.2"
            String lineaconcomasycomillas = inputfile.nextLine();

            //Separar por comas
            String[] sincoma = lineaconcomasycomillas.split(",");

            //Llenar espacios vacios
            for (int i = 0; i < sincoma.length; i++) {
                if(sincoma[i].length()==0){
                    sincoma[i] = "0.0";
                }
            }

            String[] palabraslimpias = new String[8];

            //Quitar comillas
            String word;
            for (int i = 0; i < sincoma.length; i++) {
                word = "";
                for (int j = 0; j < sincoma[i].length(); j++) {
                    if(sincoma[i].charAt(j) != '"'){
                        word += sincoma[i].charAt(j);
                    }
                }
                palabraslimpias[i] =  word;
            }


            if(sincoma.length<8){ //Si a la linea le faltan datos, llenar
                String[] sincoma8 = {"","","","","0.0","0.0","0.0","0.0"};
                for (int i = 0; i < sincoma.length; i++) {
                  sincoma8[i] = palabraslimpias[i];
                }

                for (int i = 0; i < palabraslimpias.length; i++) {
                  palabraslimpias[i] = sincoma8[i];
                }

            }

            String station = palabraslimpias[0];
            String name = palabraslimpias[1];
            //Me salto el pais
            String Date = palabraslimpias[3];
            double PRCP= Double.parseDouble(palabraslimpias[4]); //Al estar en NextLine hay que convertir String a Double
            double TAVG= Double.parseDouble(palabraslimpias[5]);
            double TMAX= Double.parseDouble(palabraslimpias[6]);
            double TMIN= Double.parseDouble(palabraslimpias[7]);

            datos.add(new Dato(station,name,Date,PRCP,TAVG,TMAX,TMIN));



        }
        System.out.println("Archivo leido y cargado");

    }

    public void guardarDatos(ArrayList<Dato> datos) throws FileNotFoundException {
        Scanner input  = new Scanner(System.in);


        System.out.print("Que nombre quiere darle al archivo?\nArchivo: ");
        String nomSalida = input.nextLine();
        String path = nomSalida+".txt";

        PrintStream output = null; //yo xd
        PrintWriter writer = new PrintWriter(path); //SImon
        try {
            output = new PrintStream(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no se pudo guardar");
        }

        if (output != null) {
            output.println("Station\t         Name\t            Date     PRCP TAVG  TMAX TMIN");
            for (Dato d : datos) {
              output.println(d.getStation()+" "+d.getName()+"\t"+d.getDate()+"   "+d.getPRCP()+" "+d.getTAVG()+"  "+d.getTMAX()+"  "+d.getTMIN());
            }
        }

        if (output != null) {
            writer.println("Station\t         Name\t            Date     PRCP TAVG  TMAX TMIN");
            for (Dato d : datos) {
                writer.println(d.getStation()+" "+d.getName()+"\t"+d.getDate()+"   "+d.getPRCP()+" "+d.getTAVG()+"  "+d.getTMAX()+"  "+d.getTMIN());
            }
        }

        writer.close();
        System.out.println("Archivo guardado!");

    }

    public void menoresQue(int n){
        Scanner input = new Scanner(System.in);
        System.out.print("Los datos menores que?\nNumero: ");
        int menores = input.nextInt();

        switch (n) {
            case 1 -> {
                for (int i = 0; i<datos.size(); i++) {
            if (datos.get(i).getPRCP() > menores){
                datos.remove(i);
                i -=1;
            }
        }
            }
            case 2 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getTAVG() > menores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getTMAX() > menores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
            case 4 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getTMIN() > menores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
        }


    }

    public void mayoresQue(int n){
        Scanner input = new Scanner(System.in);
        System.out.print("Los datos mayores que?\nNumero: ");
        double mayores = input.nextDouble();

        switch (n) {
            case 1 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getPRCP() < mayores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
            case 2 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getTAVG() < mayores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getTMAX() < mayores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
            case 4 -> {
                for (int i = 0; i<datos.size(); i++) {
                    if (datos.get(i).getTMIN() < mayores){
                        datos.remove(i);
                        i -=1;
                    }
                }
            }
        }


    }

    public int cualDato(){
        Scanner input = new Scanner(System.in);
        //PRCP TAVG  TMAX TMIN
        System.out.print("Sobre que dato quiere medir(Escriba numero)?\n" +
                "1.PRCP - 2.TAVG - 3.TMAX - 4.TMIN \nOpcion: ");
        int opcion = input.nextInt();
        if(opcion>0 && opcion<5){
            return opcion;
        }
        else {
            System.out.println("Opcion incorrecta");
            return -1;
        }

    }

    public double moda(int n){
        double datoAcomparar, moda = 0;
        int conteo, conteomax=0;

        switch (n){
            case 1:
                for (int i = 0; i < datos.size(); i++) {
                    conteo = 0;
                    datoAcomparar = datos.get(i).getPRCP();
                    for (Dato dato : datos) {
                        if (datoAcomparar == dato.getPRCP()) {
                            conteo++;
                        }
                    }

                    if(conteo>conteomax){
                        moda = datoAcomparar;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < datos.size(); i++) {
                    conteo = 0;
                    datoAcomparar = datos.get(i).getTAVG();
                    for (Dato dato : datos) {
                        if (datoAcomparar == dato.getTAVG()) {
                            conteo++;
                        }
                    }

                    if(conteo>conteomax){
                        moda = datoAcomparar;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < datos.size(); i++) {
                    conteo = 0;
                    datoAcomparar = datos.get(i).getTMAX();
                    for (Dato dato : datos) {
                        if (datoAcomparar == dato.getTMAX()) {
                            conteo++;
                        }
                    }

                    if(conteo>conteomax){
                        moda = datoAcomparar;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < datos.size(); i++) {
                    conteo = 0;
                    datoAcomparar = datos.get(i).getTMIN();
                    for (Dato dato : datos) {
                        if (datoAcomparar == dato.getTMIN()) {
                            conteo++;
                        }
                    }

                    if(conteo>conteomax){
                        moda = datoAcomparar;
                    }
                }
                break;
            default: System.out.println("Opcion no existente");
        }

        return moda;

    }

    public int conteo(){
       return datos.size();
    }
}
