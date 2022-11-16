package edu.epsevg.prop.lab.c4;

/**
 * Jugador aleatori
 * "Alea jacta est"
 * @author Profe
 */
public class MinMax implements Jugador, IAuto{
  private String nom;

  public MinMax()
  {
    nom = "SUDO";
  }

  // Creation of a simple heuristic function
  /*

  HEURISTICA: la heuristica escollida, anirà sumant un 1 per cada casella que tingui un fitxa del nostre color
  i restant un 1 per cada casella que tingui un fitxa de l'altre color. Així, si la heuristica es 0, vol dir que
  el tauler esta equilibrat, si es major que 0 vol dir que tenim mes fitxes que ell i si es menor que 0 vol dir
  que ell te mes fitxes que nosaltres.
  
  public int heuristica(Tauler t){
    int valor = 0;
    int mida = t.getMida();
    int[][] tauler = t.getTauler();
    for (int i = 0; i < mida; i++){
      for (int j = 0; j < mida; j++){
        if (t.getColor(i, j) == 1){
          valor += 1;
        }
        else if (t.getColor(i, j) == 2){
          valor -= 1;
        }
      }
    }
    return valor;
  }
  
  */

  /*
  PRIMERA_HEURISTICA --> Per a la primerar heuristica es miraràn només les solucións en 4 en rallla que es puguin donar tant en vertical com en horitzontal. 
  Així, si la heuristica es menys infinit, vol dir que hi ha una possible solució (en vertical o horitzontal) per a l'altre jugador i si es mes infinit, vol 
  dir que hi ha una possible solució per a nosaltres (en vertical o horitzontal). També s'aniran guardant en una variable el numero maxim de fitxes en posició
  guanyadora que tenim i s'aniran sumant a la variable, per altra banda, s'aniran restant a la mateixa variable el numero maxim de fitxes en posició guanyadora 
  que tingui l'altre jugador. Així, si la heuristica es 0, vol dir que el tauler esta equilibrat, si es major que 0 vol dir que tenim mes fitxes que ell (en possible posició guanyadora)
  i si es menor que 0 vol dir que ell te mes fitxes (en possible posició guanyadora) que nosaltres.
  */

  public int heuristica(Tauler t){
    // La variable maxima_puntuacio ens dira, en cas que no hi hagi ningun moviment guanyador, quin es el maxim de punts que podem aconseguir
    
    int mida = t.getMida();
    int max_puntuacio = 0;
    int min_puntuacio = 0;
    
    for (int i = 0; i < mida; i++){
      for (int j = 0; j < mida; j++){
        // Analitzem si hi ha moviment guanyador per a nosaltres (color 1)
        if (t.getColor(i, j) == 1){
          //4 en ratlla horitzontal
          if (j < mida - 3){
            if (t.getColor(i, j + 1) == 1 && t.getColor(i, j + 2) == 1 && t.getColor(i, j + 3) == 1){
              return Integer.MAX_VALUE;
            }
          }
          //4 en ratlla vertical
          if (i < mida - 3){
            if (t.getColor(i + 1, j) == 1 && t.getColor(i + 2, j) == 1 && t.getColor(i + 3, j) == 1){
              return Integer.MAX_VALUE;
            }
          }
          //4 en ratlla diagonal dreta
          if (i < mida - 3 && j < mida - 3){
            if (t.getColor(i + 1, j + 1) == 1 && t.getColor(i + 2, j + 2) == 1 && t.getColor(i + 3, j + 3) == 1){
              return Integer.MAX_VALUE;
            }
          }
          //4 en ratlla diagonal esquerra
          if (i < mida - 3 && j > 2){
            if (t.getColor(i + 1, j - 1) == 1 && t.getColor(i + 2, j - 2) == 1 && t.getColor(i + 3, j - 3) == 1){
              return Integer.MAX_VALUE;
            }
          }
          
          //Mirem si hi ha 3 fitxes seguides en vertical
          if (i < mida - 2){
            if (t.getColor(i + 1, j) == -1 && t.getColor(i + 2, j) == -1){
              max_puntuacio +=  100;
           }
          }
          //Mirem si hi ha 3 fitxes seguides en horitzontal
          if (j < mida - 2){
            if (t.getColor(i, j + 1) == -1 && t.getColor(i, j + 2) == -1){
              max_puntuacio += 100;
            }
          }
          //Mirem si hi ha 3 fitxes seguides en diagonal dreta  
          if (i < mida - 2 && j < mida - 2){
            if (t.getColor(i + 1, j + 1) == -1 && t.getColor(i + 2, j + 2) == -1){
              max_puntuacio += 100;
            }
          }
          // Mirem si hi ha 3 fitxes seguides en diagonal esquerra
          if (i < mida - 2 && j > 1){
            if (t.getColor(i + 1, j - 1) == -1 && t.getColor(i + 2, j - 2) == -1){
              max_puntuacio += 100;
            }
          }

          //Mirem si hi ha 2 fitxes seguides en vertical
          if (i < mida - 1){
            if (t.getColor(i + 1, j) == -1){
              max_puntuacio += 20;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en horitzontal
          if (j < mida - 1){
            if (t.getColor(i, j + 1) == -1){
             max_puntuacio += 20;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en diagonal dreta
          if (i < mida - 1 && j < mida - 1){
            if (t.getColor(i + 1, j + 1) == -1){
              max_puntuacio += 20;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en diagonal esquerra
          if (i < mida - 1 && j > 0){
            if (t.getColor(i + 1, j - 1) == -1){
              max_puntuacio += 20;
            }
          }

          // Si la puntuacio es mes gran que la maxima puntuacio, la maxima puntuacio sera la puntuacio actual
          //if (max_puntuacio < puntuacio1) max_puntuacio = puntuacio1;
          
        }
        //-----------------------------------------------------------------
        // Analitzem si hi ha moviment guanyador per al contrari (color -1)
        else if (t.getColor(i, j) == -1){
          //Mirem si hi ha 4 en ratlla horitzontal
          if (j < mida - 3){
            if (t.getColor(i, j + 1) == -1 && t.getColor(i, j + 2) == -1 && t.getColor(i, j + 3) == -1){
              return Integer.MIN_VALUE;
            }
          }
          //Mirem si hi ha 4 en ratlla vertical
          if (i < mida - 3){
            if (t.getColor(i + 1, j) == -1 && t.getColor(i + 2, j) == -1 && t.getColor(i + 3, j) == -1){
              return Integer.MIN_VALUE;
            }
          }
          //Mirem si hi ha 4 en ratlla diagonal dreta
          if (i < mida - 3 && j < mida - 3){
            if (t.getColor(i + 1, j + 1) == -1 && t.getColor(i + 2, j + 2) == -1 && t.getColor(i + 3, j + 3) == -1){
              return Integer.MIN_VALUE;
            }
          }
          //Mirem si hi ha 4 en ratlla diagonal esquerra
          if (i < mida - 3 && j > 2){
            if (t.getColor(i + 1, j - 1) == -1 && t.getColor(i + 2, j - 2) == -1 && t.getColor(i + 3, j - 3) == -1){
              return Integer.MIN_VALUE;
            }
          }
          
          //Mirem si hi ha 3 fitxes seguides en vertical
          if (i < mida - 2){
            if (t.getColor(i + 1, j) == -1 && t.getColor(i + 2, j) == -1){
              min_puntuacio += -100;
            }
          }
          //Mirem si hi ha 3 fitxes seguides en horitzontal
          if (j < mida - 2){
            if (t.getColor(i, j + 1) == -1 && t.getColor(i, j + 2) == -1){
              min_puntuacio += -100;
            }
          }
          //Mirem si hi ha 3 fitxes seguides en diagonal dreata
          if (i < mida - 2 && j < mida - 2){
            if (t.getColor(i + 1, j + 1) == -1 && t.getColor(i + 2, j + 2) == -1){
              min_puntuacio += -100;
            }
          }
          //Mirem si hi ha 3 fitxes seguides en diagonal esquerra
          if (i < mida - 2 && j > 1){
            if (t.getColor(i + 1, j - 1) == -1 && t.getColor(i + 2, j - 2) == -1){
              min_puntuacio += -100;
            }
          }

          //Mirem si hi ha 2 fitxes seguides en vertical
          if (i < mida - 1){
            if (t.getColor(i + 1, j) == -1){
              min_puntuacio += -20;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en horitzontal
          if (j < mida - 1){
            if (t.getColor(i, j + 1) == -1){
              min_puntuacio += -20;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en diagonal dreta
          if (i < mida - 1 && j < mida - 1){
            if (t.getColor(i + 1, j + 1) == -1){
              min_puntuacio += -20;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en diagonal esquerra
          if (i < mida - 1 && j > 0){
            if (t.getColor(i + 1, j - 1) == -1){
              min_puntuacio += -20;
            }
          }
        }        
      }
    }
    
    return max_puntuacio+min_puntuacio;
  }

  
  // Creació de la funcio MINIMAX
  public int[] minimax(Tauler t, int color, int profunditat, int alpha, int beta){
    // Si el joc s'ha acabat o la profunditat es 0, retornem la heuristica
    int a = heuristica(t);
    
    if (!t.espotmoure() || profunditat == 0 || a >= Integer.MAX_VALUE || a <= Integer.MIN_VALUE){
      //System.out.println("FINAL:" + a);
      int[] aux = new int[]{-1,a};
      return aux;
    }
    // Si es el torn de la maquina, busquem minimitzar
    if (color == -1){
      int[] max = new int[]{-1,0};
      for (int i = 0; i < t.getMida(); i++){
        if (t.movpossible(i)){
          // Creem un nou tauler per a realitzar el moviment
          Tauler copia = new Tauler(t);
          copia.afegeix(i, color);

          // Recursivitat: cridem a la funcio amb el nou tauler, el color contrari i la profunditat disminuida, i actualitzem el valor de beta
          int[] val = minimax(copia, 1, profunditat - 1, alpha, beta);

          if(val[0] == -1 || val[1] > max[1]){
            max[0] = i;
            max[1] = heuristica(t);
            alpha = heuristica(t);
          }
          // Si el valor de beta es menor o igual que alpha, retornem el valor de beta
          if (beta <= alpha) return max;
        }
      }
      return max;
    }

    // Si es el nostre torn, busquem maximitzar
    else{
      int[] min = new int[]{-1,0};
      for (int i = 0; i < t.getMida(); i++){
        if (t.movpossible(i)){
          // Creem un nou tauler per a realitzar el moviment
          Tauler copia = new Tauler(t);  
          copia.afegeix(i, color);

          // Recursivitat: cridem a la funcio amb el nou tauler, el color contrari i la profunditat disminuida, i actualitzem el valor de beta
          int[] val = minimax(copia, -1, profunditat - 1, alpha, beta);
          
          if(val[0] == -1 || val[1] < min[1]){
            min[0] = i;
            min[1] = heuristica(t);
            beta = heuristica(t);
          }

          // Si el valor de beta es menor o igual que alpha, retornem el valor de beta
          if (beta <= alpha) return min;
        }
      }
      return min;
    }
  }

  public int moviment(Tauler t, int color)
  {   
    int[] a = minimax(t, 1, 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
    
    // Si no hi h cap moviment bo, fem un moviment aleatori
    if (a[0] == -1){
      System.out.println("MOVIMENT ALERATORI");
      return (int)(t.getMida() * Math.random());
    }
    System.out.println("MILLOR HEURISTICA: "+a[0]+" - COLUMNA: "+a[1]);
    return a[0];

    /*
    for(int i = 0; i < t.getMida(); ++i)
    {
      if(t.movpossible(i)){
        
        if(a > heuristica)
        {
          col = i;
          heuristica = a;
        }
      }
    }
    */
    
    
  }

  public String nom()
  {
    return nom;
  }
}



