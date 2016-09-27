package org.tedu.cloudnote.dao;

import org.tedu.cloudnote.entity.Favor;

public interface FavorDao {
	public int findFavor(Favor favor);
	public void save(Favor favor);
}
