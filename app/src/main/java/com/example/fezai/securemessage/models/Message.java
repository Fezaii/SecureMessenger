package com.example.fezai.securemessage.models;


    public class Message {


        public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";

        public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";
        private String sender;
        private String receiver;
        private String content;

        private String type;


        public Message(String sender,String receiver,String content,String type) {

            this.sender = sender;
            this.content = content;
            this.receiver = receiver;
            this.type = type;
        }


        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public static boolean isPing(String msg) {
            String[] separated = msg.split("'");
            if(separated.length < 3) return false;
            if(separated[1].equals("PING")) return true;
            return false;
        }

        public static boolean isPong(String msg) {
            String[] separated = msg.split("'");
            if(separated.length < 3) return false;
            if(separated[1].equals("PONG")) return true;
            return false;
        }
        @Override
        public String toString() {
            return "sender:" + sender + "|content:" + content + "|receiver:" + receiver + "|type:" + type;
        }
    }

