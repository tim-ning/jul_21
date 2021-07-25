package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(int cardNo, String name, String address, String phone) throws SQLException {
		save("INSERT INTO tbl_borrower VALUES (?, ?, ?, ?)", new Object[] { cardNo, name, address, phone });
	}

	public void updateBorrower(int id, String column, String value) throws SQLException {
		save("UPDATE tbl_borrower SET " + column + " = ? WHERE cardNo = ?", new Object[] { value, id });
	}

	public void deleteBorrower(int id) throws SQLException {
		save("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { id });
	}

	public List<Borrower> readAllBorrowers() throws SQLException {
		return read("SELECT * FROM tbl_borrower", null);
	}

	public Borrower readBorrowerByCardNo(int cardNo) throws SQLException {
		List<Borrower> list = read("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Integer[] { cardNo });
		return (list.size() > 0) ? list.get(0) : null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Borrower(rs.getInt("cardNo"), rs.getString("name"), rs.getString("address"),
					rs.getString("phone")));
		}
		return list;
	}

}