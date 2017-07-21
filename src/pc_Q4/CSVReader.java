package pc_Q4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

public class CSVReader {

    public static void main(String[] args) {

    	String csvFile = "/Users/mkyong/csv/users.csv";
    	//usar o path do arquivo de entrada desse jeito, previne
    	//que o usuário consiga acessar por si arquivo indevidos,
    	//uma vez que se trata de uma string definida. 
    	
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
        	// estas variáveis foram passadas para dentro do try para
        	//minimizar o escopo das variáveis
        	
            String line = "";
            
            //String cvsSplitBy = ","; esta variável fora utilizada apenas
            //uma vez e com valor definido, dessa forma ela fora removida.
            
            User[] userArray = new User[2];
            //usando o Array de User como fora imposto na questao,
            //implica em ter de se tratar a injeção de códigos nesse array.
            //Para tanto, usarei whitelisting para impedir que a entrada
            //(presente no arquivo .csv) não sanitizada seja de conteúdo malicioso.
            
            User[] users = (User[]) changeArraySize(userArray, 10);
            //Aqui o tamanho do Array é passado em tempo de execução
            //como fora colocado como requisito.
            
            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] usersLine = line.split(";");
                
                for (int j = 0; j < usersLine.length; j++){
                	// dessa forma, se as strings presentes no CSV não estiverem de acordo
                	//com os caracteres whitelisted a exceção será disparada.
                	if (usersLine[j].matches("[\\w]*")) throw new IllegalArgumentException();
                }
                
                User user = new User(usersLine[0], usersLine[1], usersLine[2], 
                		usersLine[3], usersLine[4]);
                users[i]=user;
                //um problema em potencial para essa apicação seria o estouro de buffer,
                //mas como não estamos trabalhando em linguagem C, por exemplo, que poderia
                //proporcionar esse tipo de problema com funções como gets, não precisamos
                //nos preocupar com isso, neste caso.
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object changeArraySize(Object obj, int len) {
    	Class<?> arr = obj.getClass().getComponentType();
    	Object newArray = Array.newInstance(arr, len);
    	
    	//do array copy
    	int co = Array.getLength(obj);
    	System.arraycopy(obj, 0, newArray, 0, co);
    	
    	return newArray;
    }
}
