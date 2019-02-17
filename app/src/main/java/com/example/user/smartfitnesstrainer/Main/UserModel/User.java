package com.example.user.smartfitnesstrainer.Main.UserModel;

    public class User {

        /**
         * message : Logged in as adminwdsadvxcv
         * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDk5NjI1ODEsIm5iZiI6MTU0OTk2MjU4MSwianRpIjoiNTIzOTc2MjctNWVhOS00YmEwLTg1OGEtNWQwOGE0NTBiZmE4IiwiZXhwIjoxNTQ5OTYzNDgxLCJpZGVudGl0eSI6ImFkbWlud2RzYWR2eGN2IiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNjZXNzIn0.-QH8O_drb6htaEsdde-k2VqXZlPEx7hncBC8-Tc7CAw
         * refresh_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NDk5NjI1ODEsIm5iZiI6MTU0OTk2MjU4MSwianRpIjoiNjFkNWZkOWQtM2ZlYi00MWIyLWI5YTEtZDE0YmMyODFkNzliIiwiZXhwIjoxNTUyNTU0NTgxLCJpZGVudGl0eSI6ImFkbWlud2RzYWR2eGN2IiwidHlwZSI6InJlZnJlc2gifQ.5kIB-ErFkUPivB6EeMZtQjTLPBhQhDVNLzAVIMEsjNk
         */

        private String message;
        private String access_token;
        private String refresh_token;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }

