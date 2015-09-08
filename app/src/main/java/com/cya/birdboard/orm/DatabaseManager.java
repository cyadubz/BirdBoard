package com.cya.birdboard.orm;

import com.lyblab.healthydiet.TheApplication;
import com.lyblab.healthydiet.model.Group;
import com.lyblab.healthydiet.model.Product;
import com.lyblab.healthydiet.model.Reception;
import com.lyblab.healthydiet.model.UserInfo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ����� on 11.06.2015.
 */
public class DatabaseManager {

    static private DatabaseManager instance;

    private DatabaseHelper helper;

    public DatabaseManager() {
    }

    static public DatabaseManager getInstance() {
        if (null == instance) {
            instance = new DatabaseManager();
            instance.helper = new DatabaseHelper(TheApplication.getAppContext());
        }
        return instance;
    }

    public void createOrUpdateUserInfo(UserInfo userInfo) {
        try {
            helper.getUserInfoDao().createOrUpdate(userInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserInfo getUserInfoByName(String name) {
        UserInfo userInfo = null;
        try {
            userInfo = helper.getUserInfoDao().queryBuilder().where().eq("username", name).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            products = helper.getProductsDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void createOrUpdateProduct(Product product) {
        try {
            Product existed = helper.getProductsDao().queryBuilder().where().eq("number", product.getNumber()).queryForFirst();
            if (existed != null) {
                product.setId(existed.getId());
            }
            helper.getProductsDao().createOrUpdate(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOrUpdateGroup(Group group) {
        try {
            helper.getGroupsDao().createOrUpdate(group);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createGroup(String title) {
        Group group = new Group();
        group.setTitle(title);
        try {
            helper.getGroupsDao().createOrUpdate(group);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        try {
            groups = helper.getGroupsDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public Group getGroupById(long groupId) {
        Group group = new Group();
        try {
            group = helper.getGroupsDao().queryBuilder().where().eq("id", groupId).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    public void createOrUpdateReception(Reception reception) {
        try {
            helper.getReceptionsDao().createOrUpdate(reception);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reception> getReceptions() {
        List<Reception> receptions = new ArrayList<>();
        try {
            receptions = helper.getReceptionsDao().queryBuilder().groupBy("groupId").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return receptions;
    }

    public Product getProductById(String productId) {
        Product product = new Product();
        try {
            product = helper.getProductsDao().queryForId(Integer.valueOf(productId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public Product getProductById(long productId) {
        Product product = new Product();
        try {
            product = helper.getProductsDao().queryForId((int) productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public Reception getReception() {
        Reception reception = new Reception();
        Date date = new Date(System.currentTimeMillis());
        //helper.getReceptionsDao().queryBuilder().
        return reception;
    }
}
