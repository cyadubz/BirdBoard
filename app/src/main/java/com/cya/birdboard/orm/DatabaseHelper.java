package com.cya.birdboard.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lyblab.healthydiet.model.Group;
import com.lyblab.healthydiet.model.Product;
import com.lyblab.healthydiet.model.Reception;
import com.lyblab.healthydiet.model.UserInfo;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "HealthyDiet.db";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;


    private Dao<UserInfo, Integer> userInfo = null;
    private Dao<Product, Integer> products = null;
    private Dao<Reception, Integer> receptions = null;
    private Dao<Group, Integer> groups = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, Product.class);
            TableUtils.createTable(connectionSource, Reception.class);
            TableUtils.createTable(connectionSource, Group.class);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, UserInfo.class, true);
            TableUtils.dropTable(connectionSource, Product.class, true);
            TableUtils.dropTable(connectionSource, Reception.class, true);
            TableUtils.dropTable(connectionSource, Group.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Dao<UserInfo, Integer> getUserInfoDao() {
        if (null == userInfo) {
            try {
                userInfo = getDao(UserInfo.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    public Dao<Product, Integer> getProductsDao() {
        if (null == products) {
            try {
                products = getDao(Product.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public Dao<Reception, Integer> getReceptionsDao() {
        if (null == receptions) {
            try {
                receptions = getDao(Reception.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return receptions;
    }

    public Dao<Group, Integer> getGroupsDao() {
        if (null == groups) {
            try {
                groups = getDao(Group.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }
}
