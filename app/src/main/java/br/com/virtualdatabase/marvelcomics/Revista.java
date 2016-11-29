package br.com.virtualdatabase.marvelcomics;

import java.io.Serializable;

/**
 * Created by marcoscesteves on 06/06/16.
 */
public class Revista implements Serializable, Comparable{
    private Double issueNumber;
    private int id;
    private String description;
    private String title;
    private String pageCount;
    private Thumb thumbnail = new Thumb();
    private Price[] prices = {new Price(), new Price(), new Price()};
    private ReleaseDates[] dates = {new ReleaseDates(), new ReleaseDates(), new ReleaseDates()};

    /**
     * Há várias datas dentro de cada objeto. Este método filtra e disponibiliza uma destas ("on Sale Date").
     * @return - on Sale DATE
     */
    public String getDates() {
        String date= null;
        for (ReleaseDates var : dates){

            if (var.type.contains("onsale")){
                date = var.date.substring(0,10);
            }}

        return date;

    }

    public void setDates(ReleaseDates[] dates) {
        this.dates = dates;
    }

    /**
     * Método que fornece o preço da Revista a partir do parâmetro de entrada
     * @param select - "print" - para preço da revista impressa; "digital" para preço
     *               da revista digital.
     * @return - fornece o valor da revista selecionada (Double)
     */
    public double getPrices(String select) {
        double saida = 0.0;

        switch (select){
            case "paper":

                for (Price variavel : prices){
                    if (variavel.type.toString().contains("print")){
                        saida = variavel.price;  }  }
                break;

            case "digital":

                for (Price variavel : prices){
                    if (variavel.type.toString().contains("digital")){
                        saida = variavel.price; } }
                break;}
        return saida;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    Revista(String descript){
        this.description = descript;

    }


    Revista(){

    }

    /**
     * Método que devolve o "endereço" de uma imagem a partir do parâmetro de entrada
     * @param escolha - "pequena" - imagem pequena (150x225px), "media" - imagem
     *                média (168x252px), "grande" - imagem grande (300x450px)
     * @return
     */
    public String getThumbnail(String escolha) {
        if (escolha.contains("pequena")){
            return thumbnail.path+"/portrait_xlarge.jpg";
        } else if (escolha.contains("grande")){
            return thumbnail.path+"/portrait_uncanny.jpg";
        } else {
            return thumbnail.path+"/portrait_incredible.jpg";
        }

    }

    public void setThumbnail(Thumb thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Double getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Double issueNumber) {
        this.issueNumber = issueNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Método responsável pela cmparação dos objetos (Revistas) utilizando por parâmetro o "Issue Number"
     * @param another
     * @return
     */
    @Override
    public int compareTo(Object another) {
        Revista outraRevista = new Revista();
        outraRevista = (Revista) another;
        if (this.issueNumber > outraRevista.getIssueNumber()){
            return 1;
        } else if (this.issueNumber < outraRevista.getIssueNumber()){
            return -1;
        } else {
            return 0;
        }

    }


    class Thumb implements Serializable{
        private String path;
        private String extension;
    }

    class Price implements Serializable{
        private String type;
        private double price;
    }

    class ReleaseDates implements Serializable{
        private String type;
        private String date;
    }
}
