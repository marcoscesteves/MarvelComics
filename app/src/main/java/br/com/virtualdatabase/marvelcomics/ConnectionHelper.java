package br.com.virtualdatabase.marvelcomics;

import java.util.ArrayList;

/**
 * Created by marcoscesteves on 07/06/16.
 */
public class ConnectionHelper {
    private int code;
    private String status;
    private String copyright;
    private String attributionText;
    private String attributionHTML;
    private String eTag;
    private Data data = new Data();
    private ArrayList<Revista> listaRevistas = new ArrayList<>();

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    class Data {
        private int offset;
        private int limit;
        private int total;
        private int count;
        private Revista[] results;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Revista[] getResults() {
            return results;
        }


        public void setResults(Revista[] results) {
            this.results = results;
        }
    }


}



