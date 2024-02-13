
    public static void main(String[] args) {

        Random random = new Random();    
       UUID uuid = UUID.randomUUID();
        int mes = 03;
        int dia = 1;
        String userUuid = "e51739bb-9b6a-4db7-826c-93627781f4d1";
        String horaEntrada = voltar0(random.nextInt(7,13));
        String minutoEntrada = voltar0(random.nextInt(0,60));
        String segundoentrada = voltar0(random.nextInt(0,60));

        String horaSaida = voltar0(random.nextInt(14,20));
        String muntoSaida = voltar0(random.nextInt(0,60));
        String segundoSaida = voltar0(random.nextInt(0,60));

        gerarSQL(horaEntrada, minutoEntrada, segundoentrada, uuid, Integer.toString(mes), Integer.toString(dia),userUuid,1);
     
    
    }

    public static  String voltar0(int numero){
        String n = Integer.toString(numero);
        if(n.length()==1){
            return "0" + n;
        }return n;
    }

    public static void gerarSQL(String hora, String minuto, String segundo, UUID uuid, String mes, String dia, String userUuid,int status){

        String insert = "INSERT INTO REGISTER_POINT(id,date,status,id_user) VALUES (";
        String anoMesDia =  "'" + uuid+"'" + ", "+ "'"+ 2024 +"-0"+ mes +"-"+ "0" + dia;
        String horaMinutoSegundo = " " + hora + ":" + minuto + ":" + segundo + "'," + status +",'" + userUuid + "'" +");";


       System.out.println(insert + anoMesDia +horaMinutoSegundo );

    }
    