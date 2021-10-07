package com.gabriela.Pojisteni.model;

import javax.persistence.*;
@Entity
@Table(name="insurance")
public class Insurance {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "insurance_id")
        private Integer insurance_id;
        @Column(name = "nazev", nullable = false)
        private String title;
        @Column(name = "detail")
        private String detail;
        @Column(name = "platnost_od", nullable = false)
        private String start;
        @Column(name = "platnost_do", nullable = false)
        private String end;
        @Column(name = "cislo_smlouvy", nullable = false, length = 45)
        private String contract;

        // Napojení na uživatele (pojištěnce), přes cizí klíč id uživatele
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id")
        private User user;

        public Insurance() {}

        public Integer getInsurance_id() {
            return insurance_id;
        }

        public void setInsurance_id(Integer insurance_id) {
            this.insurance_id = insurance_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
}
