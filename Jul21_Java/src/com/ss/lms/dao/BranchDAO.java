package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Branch;

public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection conn) {
		super(conn);
	}

	public void addBranch(int id, String name, String address) throws SQLException {
		save("INSERT INTO tbl_library_branch VALUES (?, ?, ?)", new Object[] { id, name, address });
	}

	public void updateBranch(int id, String column, String value) throws SQLException {
		save("UPDATE tbl_library_branch SET " + column + " = ? WHERE branchId = ?", new Object[] { value, id });
	}

	public Branch readBranchById(int id) throws SQLException {
		List<Branch> list = read("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] { id });
		return (list.size() > 0) ? list.get(0) : null;
	}

	public List<Branch> readAllBranches() throws SQLException {
		return read("SELECT * FROM tbl_library_branch", null);
	}

	// get a list of branches where this borrower has books checked out.
	// only includes rows where dateIn is null.
	public List<Branch> readBranchesByCardNo(int cardNo) throws SQLException {
		return read("SELECT * FROM tbl_book_loans bl INNER JOIN tbl_library_branch lb ON lb.branchId = bl.branchId "
				+ "WHERE cardNo = ? AND dateIn IS NULL GROUP BY branchName", new Object[] { cardNo });
	}

	public void deleteBranch(int id) throws SQLException {
		save("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { id });
	}

	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Branch(rs.getInt("branchId"), rs.getString("branchName"), rs.getString("branchAddress")));
		}
		return list;
	}

}
