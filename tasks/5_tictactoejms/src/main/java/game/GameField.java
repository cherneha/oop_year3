package game;

public class GameField {
    private String field[][];
    public GameField(){
        field = new String[][] {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
    }
    public void drawField(){
        System.out.print("\033[H\033[2J");
        for(int i = 0; i < 3; i++) {
            System.out.printf("| %s | %s | %s |\n", field[i][0], field[i][1], field[i][2]);
            System.out.println("-----------");
        }
    }
    public void editField(int cell, String sign){
        int x, y;
        switch (cell){
            case 1:
                x = 0; y = 0;
                break;
            case 2:
                x = 0; y = 1;
                break;
            case 3:
                x = 0; y = 2;
                break;
            case 4:
                x = 1; y = 0;
                break;
            case 5:
                x = 1; y = 1;
                break;
            case 6:
                x = 1; y = 2;
                break;
            case 7:
                x = 2; y = 0;
                break;
            case 8:
                x = 2; y = 1;
                break;
            case 9:
                x = 2; y = 2;
                break;
            default:
                return;
        }
        if(field[x][y] != "x" || field[x][y] != "o") {
            field[x][y] = sign;
        }
    }
    public boolean winner(){

        for(int i = 0; i < 3; i++){
            if(field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2])){
                if(field[i][0].equals(Main.player)){
                    System.out.println("You won!");
                    return true;
                }
                else{
                    System.out.println("You lost!");
                    return true;
                }
            }
            if(field[0][i].equals(field[1][i]) && field[1][i].equals(field[2][i])){
                if(field[0][i].equals(Main.player)){
                    System.out.println("You won!");
                    return true;
                }
                else{
                    System.out.println("You lost!");
                    return true;
                }
            }
        }
        if(field[0][2].equals(field[1][1]) && field[2][0].equals(field[1][1])){
            if(field[0][2].equals(Main.player)){
                System.out.println("You won!");
                return true;
            }
            else{
                System.out.println("You lost!");
                return true;
            }
        }
        if(field[0][0].equals(field[1][1]) && field[2][2].equals(field[1][1])){
            if(field[0][0].equals(Main.player)){
                System.out.println("You won!");
                return true;
            }
            else{
                System.out.println("You lost!");
                return true;
            }
        }
        return false;
    }
}
