package com.zst.ynh.bean;

public class ApplySuccessBean  {
    private String money_amount;
    private String counter_fee;
    private String loan_term;
    private String detail_url;
    private String apply_date;
    private DialogBean dialog;

    public String getMoney_amount() {
        return money_amount;
    }

    public void setMoney_amount(String money_amount) {
        this.money_amount = money_amount;
    }

    public String getCounter_fee() {
        return counter_fee;
    }

    public void setCounter_fee(String counter_fee) {
        this.counter_fee = counter_fee;
    }

    public String getLoan_term() {
        return loan_term;
    }

    public void setLoan_term(String loan_term) {
        this.loan_term = loan_term;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public DialogBean getDialog() {
        return dialog;
    }

    public void setDialog(DialogBean dialog) {
        this.dialog = dialog;
    }

    public static class DialogBean {
        private String id;
        private String img_url;
        public Active active;
        public CloseOrigin close_origin;

        public Active getActive() {
            return active;
        }

        public void setActive(Active active) {
            this.active = active;
        }

        public CloseOrigin getClose_origin() {
            return close_origin;
        }

        public void setClose_origin(CloseOrigin close_origin) {
            this.close_origin = close_origin;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public static class Active{
            private String skip_code;
            private  String url;

            public String getSkip_code() {
                return skip_code;
            }

            public void setSkip_code(String skip_code) {
                this.skip_code = skip_code;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
        public static class CloseOrigin{
            private String x;
            private  String y;

            public String getX() {
                return x;
            }

            public void setX(String x) {
                this.x = x;
            }

            public String getY() {
                return y;
            }

            public void setY(String y) {
                this.y = y;
            }
        }
    }
}
