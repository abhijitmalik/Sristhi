package com.srishti.sda;

public class Model {
    public String ArrangerCode, ArrangerName, SponsorCode, Rank, FormNo, OfficeID, DateOfJoin,
            DateOfEnt, ArrangerDOB, VoucherNo, Father, Address, Email, Phone, Nominee, NomineeDOB, NomineeRelation;

    public Model(String arrangerCode, String arrangerName, String sponsorCode, String rank, String formNo,
                 String officeID, String dateOfJoin, String dateOfEnt, String arrangerDOB, String voucherNo,
                 String father, String address, String email, String phone, String nominee,
                 String nomineeDOB, String nomineeRelation) {
        this.ArrangerCode = arrangerCode;
        this.ArrangerName = arrangerName;
        this.SponsorCode = sponsorCode;
        this.Rank = rank;
        this.FormNo = formNo;
        this.OfficeID = officeID;
        this.DateOfJoin = dateOfJoin;
        this.DateOfEnt = dateOfEnt;
        this.ArrangerDOB = arrangerDOB;
        this.VoucherNo = voucherNo;
        this.Father = father;
        this.Address = address;
        this.Email = email;
        this.Phone = phone;
        this.Nominee = nominee;
        this.NomineeDOB = nomineeDOB;
        this.NomineeRelation = nomineeRelation;
    }
}
