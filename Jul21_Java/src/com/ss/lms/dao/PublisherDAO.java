package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public void addPublisher(int id, String name, String address, String phone) throws SQLException {
		save("INSERT INTO tbl_publisher (publisherId, publisherName, publisherAddress, publisherPhone) VALUES (?, ?, ?, ?)",
				new Object[] { id, name, address, phone });
	}

	public void updatePublisher(int id, String column, String value) throws SQLException {
		save("UPDATE tbl_publisher SET " + column + " = ? WHERE publisherId = ?", new Object[] { value, id });
	}

	public void deletePublisher(int id) throws SQLException {
		save("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] { id });
	}

	public Publisher readPublisherById(int id) throws SQLException {
		List<Publisher> list = read("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[] { id });
		return (list.size() > 0) ? list.get(0) : null;
	}

	public List<Publisher> readAllPublishers() throws SQLException {
		return read("SELECT * FROM tbl_publisher", null);
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> list = new ArrayList<>();
		while (rs.next()) {
			list.add(new Publisher(rs.getInt("publisherId"), rs.getString("publisherName"),
					rs.getString("publisherAddress"), rs.getString("publisherPhone")));
		}
		return list;
	}

}
