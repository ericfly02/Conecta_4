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
  PRIMERA_HEURISTICA: Per a la primerar heuristica es miraràn només les solucións en 4 en rallla que es puguin donar tant en vertical com en horitzontal. 
  Així, si la heuristica es menys infinit, vol dir que hi ha una possible solució (en vertical o horitzontal) per a l'altre jugador i si es mes infinit, vol 
  dir que hi ha una possible solució per a nosaltres (en vertical o horitzontal). També s'aniran guardant en una variable el numero maxim de fitxes en posició
  guanyadora que tenim i s'aniran sumant a la variable, per altra banda, s'aniran restant a la mateixa variable el numero maxim de fitxes en posició guanyadora 
  que tingui l'altre jugador. Així, si la heuristica es 0, vol dir que el tauler esta equilibrat, si es major que 0 vol dir que tenim mes fitxes que ell (en possible posició guanyadora)
  i si es menor que 0 vol dir que ell te mes fitxes (en possible posició guanyadora) que nosaltres.
  */

  
  
  public int heuristica(Tauler t){
    // La variable maxima_puntuacio ens dira, en cas que no hi hagi ningun moviment guanyador, quin es el maxim de punts que podem aconseguir
    
    int mida = t.getMida();
    int color = 0;
    int max_puntuacio = 0;
    int min_puntuacio = 0;

    for (int i = 0; i < mida; i++){
      for (int j = 0; j < mida; j++){
        // si la posició actual te una fitxa nostra, sumem 1 a la variable color per a comprovar amb la seguent posició
        if (t.getColor(i, j) == 1){
          color = 1;
        }
        // si la posició actual te una fitxa nostra, sumem -1 a la variable color per a comprovar amb la seguent posició
        else if (t.getColor(i, j) == -1){
          color = -1;
        }
        // Analitzem si hi ha moviment guanyador per a nosaltres (color 1)
        if (color == 1){
          int puntuacio1 = 1;
          if (j < mida - 3){
            if (t.getColor(i, j + 1) == 1 && t.getColor(i, j + 2) == 1 && t.getColor(i, j + 3) == 1){
              return Integer.MAX_VALUE;
            }
          }
          if (i < mida - 3){
            if (t.getColor(i + 1, j) == 1 && t.getColor(i + 2, j) == 1 && t.getColor(i + 3, j) == 1){
              return Integer.MAX_VALUE;
            }
          }
          
          //Mirem si hi ha 3 fitxes seguides en vertical
          if (i < mida - 2){
            if (t.getColor(i + 1, j) == -1 && t.getColor(i + 2, j) == -1){
              if(puntuacio1 < 3) puntuacio1 = 3;
            }
          }
          //Mirem si hi ha 3 fitxes seguides en horitzontal
          if (j < mida - 2){
            if (t.getColor(i, j + 1) == -1 && t.getColor(i, j + 2) == -1){
              if(puntuacio1 < 3) puntuacio1 = 3;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en vertical
          if (i < mida - 1){
            if (t.getColor(i + 1, j) == -1){
              if(puntuacio1 < 2) puntuacio1 = 2;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en horitzontal
          if (j < mida - 1){
            if (t.getColor(i, j + 1) == -1){
             if(puntuacio1 < 2) puntuacio1 = 2;
            }
          }
          if (max_puntuacio < puntuacio1) max_puntuacio = puntuacio1;
          
        }
        //-----------------------------------------------------------------
        // Analitzem si hi ha moviment guanyador per al contrari (color -1)
        else if (color == -1){
            int puntuacio2 = -1;
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
          
          //Mirem si hi ha 3 fitxes seguides en vertical
          if (i < mida - 2){
            if (t.getColor(i + 1, j) == -1 && t.getColor(i + 2, j) == -1){
              if(puntuacio2 > -3) puntuacio2 = -3;
            }
          }
          //Mirem si hi ha 3 fitxes seguides en horitzontal
          if (j < mida - 2){
            if (t.getColor(i, j + 1) == -1 && t.getColor(i, j + 2) == -1){
              if(puntuacio2 > -3) puntuacio2 = -3;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en vertical
          if (i < mida - 1){
            if (t.getColor(i + 1, j) == -1){
              if(puntuacio2 > -2) puntuacio2 = -2;
            }
          }
          //Mirem si hi ha 2 fitxes seguides en horitzontal
          if (j < mida - 1){
            if (t.getColor(i, j + 1) == -1){
              if(puntuacio2 > -2) puntuacio2 = -2;
            }
          }
          if (min_puntuacio > puntuacio2) max_puntuacio = puntuacio2;
          
        }        
      }
    }
    return max_puntuacio+min_puntuacio;
  }

  
  // Creació de la funcio MINIMAX
  public int[] minimax(Tauler t, int color, int profunditat, int alpha, int beta){
    // Si el joc s'ha acabat o la profunditat es 0, retornem la heuristica
    System.out.println("MINIMAX");
    System.out.println(profunditat);
    int a = heuristica(t);
    
    if (!t.espotmoure() || profunditat == 0 || a >= Integer.MAX_VALUE || a <= Integer.MAX_VALUE){
      System.out.println(a);
      int[] aux = new int[]{-1,a};
      return aux;
    }
    // Si es el torn de la maquina, busquem el maxim
    if (color == -1){
      int[] max = new int[]{-1,0};
      for (int i = 0; i < t.getMida(); i++){
        if (t.movpossible(i)){
          Tauler copia = new Tauler(t);
          copia.afegeix(i, color);
          int[] val = minimax(copia, 1, profunditat - 1, alpha, beta);
          //max = Math.max(max, val);
          if(val[0] == -1 || val[1] > max[1])
          {
            val[0] = i;
            max[1] = heuristica(t);
            alpha = heuristica(t);
          }
          if (beta <= alpha) return max;
        }
      }
      return max;
    }
    // Si es el nostre torn, busquem el minim
    else{
      int[] min = new int[]{-1,0};
      for (int i = 0; i < t.getMida(); i++){
        if (t.movpossible(i)){
          Tauler copia = new Tauler(t);  
          copia.afegeix(i, color);
          int[] val = minimax(copia, -1, profunditat - 1, alpha, beta);
          //min = Math.min(min, val);
          if(val[0] == -1 || val[1] < min[1])
          {
            min[0] = i;
            min[1] = heuristica(t);
            beta = heuristica(t);
          }
          if (beta <= alpha) return min;
        }
      }
      return min;
    }
  }

  public int moviment(Tauler t, int color)
  {
    /*
    int col = (int)(t.getMida() * Math.random());
    while (!t.movpossible(col)) {
      col = (int)(t.getMida() * Math.random());
    }
    return col;
    */
    

    int[] a = minimax(t, 1, 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
    System.out.println(a);
    
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
    
    return a[0];
  }

  public String nom()
  {
    return nom;
  }
}



