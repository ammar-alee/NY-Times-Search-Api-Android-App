package com.interview.ammaryali.model;

import java.util.List;

public class ArticleResponse {

    private String status;
    private String copyright;
    private ResponseBean response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private List<Article> docs;

        public List<Article> getDocs() {
            return docs;
        }

        public void setDocs(List<Article> docs) {
            this.docs = docs;
        }
    }
}