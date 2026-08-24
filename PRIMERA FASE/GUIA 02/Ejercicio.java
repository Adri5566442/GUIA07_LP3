interface Detalle {
    void ver();
}

abstract class Persona implements Detalle {
    protected String nom;
    protected String ape;

    public Persona(String n, String a) {
        nom = n;
        ape = a;
    }

    public abstract void ver();
}

class Profesor extends Persona {
    private String esp;

    public Profesor(String n, String a, String e) {
        super(n, a);
        esp = e;
    }

    public void ver() {
        System.out.println("Profesor: " + nom + " " + ape + " - " + esp);
    }
}

class Estudiante extends Persona {
    private int cod;
    public static int tot = 0;

    public Estudiante(String n, String a, int c) {
        super(n, a);
        cod = c;
        tot++;
    }

    public void ver() {
        System.out.println("Estudiante: " + nom + " " + ape + " - " + cod);
    }
    
    public static int getTot() {
        return tot;
    }
}

class Curso implements Detalle {
    private String nomC;
    private String cat;
    private Profesor prof;
    private Estudiante[] ests;
    private int numE;
    public final static int MAX = 30;

    public Curso(String n, String c, Profesor p) {
        nomC = n;
        cat = c;
        prof = p;
        ests = new Estudiante[MAX];
        numE = 0;
    }

    public void addE(Estudiante e) {
        if (numE < MAX) {
            ests[numE] = e;
            numE++;
        }
    }

    public String getNomC() {
        return nomC;
    }

    public void ver() {
        System.out.println("Curso: " + nomC + " | Categoria: " + cat);
        prof.ver();
        System.out.println("Inscritos: " + numE + " / " + MAX);
        for (int i = 0; i < numE; i++) {
            System.out.print("  - ");
            ests[i].ver();
        }
        System.out.println("---");
    }
}

class SisGes {
    private Curso[] curs;
    private int numC;

    public SisGes() {
        curs = new Curso[20];
        numC = 0;
    }

    public void addC(Curso c) {
        if (numC < 20) {
            curs[numC] = c;
            numC++;
        }
    }

    public void mosDis() {
        System.out.println("Cursos Disponibles:");
        for (int i = 0; i < numC; i++) {
            System.out.println("- " + curs[i].getNomC());
        }
        System.out.println("---");
    }

    public void mosDet() {
        System.out.println("Detalle de Cursos:");
        for (int i = 0; i < numC; i++) {
            curs[i].ver();
        }
        System.out.println("Total estudiantes en el sistema: " + Estudiante.getTot());
    }
}

public class Ejercicio {
    public static void main(String[] args) {
        SisGes sis = new SisGes();

        Profesor p1 = new Profesor("Carlos", "Gomez", "Matematicas");
        Profesor p2 = new Profesor("Ana", "Perez", "Programacion");

        Curso c1 = new Curso("Mate Basica", "Matematicas", p1);
        Curso c2 = new Curso("Java POO", "Programacion", p2);

        sis.addC(c1);
        sis.addC(c2);

        Estudiante e1 = new Estudiante("Luis", "Rios", 101);
        Estudiante e2 = new Estudiante("Maria", "Sol", 102);
        Estudiante e3 = new Estudiante("Juan", "Diaz", 103);

        c1.addE(e1);
        c1.addE(e2);
        
        c2.addE(e2);
        c2.addE(e3);

        sis.mosDis();
        sis.mosDet();
    }
}