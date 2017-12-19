package dev.majes.base.database;



import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.greenrobot.greendao.rx.RxDao;

import java.util.List;

import dev.majes.base.DevLibApplication;
import dev.majes.base.database.db.DaoMaster;
import dev.majes.base.database.db.DaoSession;

/**
 * @author majes
 */

public class DaoManager {
    private static final String DB_NAME = "dev_database";
    private volatile static DaoManager manager;
    private static MySQLiteOpenHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private DaoManager() {
    }

    public static void initDaoManager() {
        helper = new MySQLiteOpenHelper(DevLibApplication.getDevLibApplicationContext(), DB_NAME, null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DaoManager getDaoManager() {
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (manager == null) {
                    manager = new DaoManager();
                }
            }
        }
        return manager;
    }

    private DaoSession getDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
        }
        if (daoSession == null) {
            synchronized (DaoManager.class) {
                if (daoMaster == null) {
                    daoMaster = getDaoMaster();
                }
                daoSession = daoMaster.newSession();
            }
        }
        return daoSession;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            synchronized (DaoManager.class){
                if (null == daoMaster){
                    // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
                    // 所以实现数据库的安全升级。利用MrigrtionHelper，网上copy的。
                    helper = new MySQLiteOpenHelper(DevLibApplication.getDevLibApplicationContext(), DB_NAME, null);
                    daoMaster = new DaoMaster(helper.getWritableDatabase());
                    //daoMaster = new DaoMaster(helper.getEncryptedWritableDb("123")); //加密 uuid
                }
            }
        }
        return daoMaster;
    }

    public void setDebug(boolean isDebug) {
        QueryBuilder.LOG_SQL = isDebug;
        QueryBuilder.LOG_VALUES = isDebug;
    }

    public void closeConnection() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }
    /**
     * List<User> list=DBUtils.getQueryBuilder(User.class)
     * .where(UserDao.Properties.Size.ge(5))
     * .orderAsc(UserDao.Properties.Id)
     * .list();
     *
     * @param clazz
     * @param <T>
     * @return QueryBuilder<T>
     */
    public synchronized  <T> QueryBuilder<T> getQueryBuilder(Class<T> clazz) {
        try {
            QueryBuilder<T> queryBuilder = null;

            if (clazz != null) {
                queryBuilder = (QueryBuilder<T>) getDaoSession().getDao(clazz).queryBuilder();
            }

            return queryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 插入或替换单个对象(没有就插入,有就更新)
     * 由ID确定唯一
     * 1.ID为空自增长和唯一索引，每次插入根据唯一索引更新或者插入，id子增长
     * 2.ID为空自增长和无唯一索引，每次都当成新数据插入，id子增长
     * 3.ID不为空，则不自动增长,且清空所有数据每次只更新一条数据
     * 4.数据库更新时，插入没有的字段，则搬运数据将以前数据删除，重新插入
     *
     * @param
     * @return true 替换成功
     */
    public synchronized <T> boolean insertOrReplace(T t) {
        boolean flag = false;
        try {
            if (t != null) {
                flag = getDaoSession().insertOrReplace(t) != -1 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 插入或替换多个对象
     * 由ID确定唯一
     * 1.ID为空自增长和唯一索引，每次插入根据唯一索引更新或者插入，id子增长
     * 2.ID为空自增长和无唯一索引，每次都当成新数据插入，id子增长
     * 3.ID不为空，则不自动增长,且清空所有数据每次只更新一条数据
     *
     * @param
     * @return
     */
    public synchronized  <T> boolean insertOrReplaceList(Class clazz, List<T> list) {
        boolean flag = false;
        try {
            if (clazz != null && list != null && !list.isEmpty()) {
                getDaoSession().getDao(clazz).insertOrReplaceInTx(list);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 通过传入实例删除单个对象
     * 1.要全部属性匹配上才能删除
     * DBUtils.delete(new User(Long.parseLong("5"),"pp","5",3))
     *
     * @param
     * @return
     */
    public synchronized <T> boolean delete(T t) {
        boolean flag = false;
        try {
            if (t != null) {
               getDaoSession().delete(t);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 按条件(where)删除对象
     * DBUtils.deleteWhere(User.class, UserDao.Properties.Name.eq("tst"));
     *
     * @param
     * @return
     */
    public synchronized boolean deleteWhere(Class clazz, WhereCondition cond, WhereCondition... condMore) {
        boolean flag = false;
        try {
            if (clazz != null && cond != null) {
                getDaoSession().getDao(clazz).queryBuilder().where(cond, condMore)
                        .buildDelete().forCurrentThread().executeDeleteWithoutDetachingEntities();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 按条件(cond1或cond2)删除对象
     *
     * @param
     * @return
     */
    public synchronized boolean deleteWhereOr(Class clazz, WhereCondition cond1, WhereCondition cond2, WhereCondition... condMore) {
        boolean flag = false;
        try {
            if (clazz != null && cond1 != null && cond2 != null) {
               getDaoSession().getDao(clazz).queryBuilder()
                        .whereOr(cond1, cond2, condMore).buildDelete().forCurrentThread()
                        .executeDeleteWithoutDetachingEntities();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除list数据
     * 需要全部属性对上
     *
     * @param clazz 字节码对象
     * @param list  需要删除的数据的集合
     * @return
     */
    public synchronized <T> boolean deleteList(Class clazz, List<T> list) {
        boolean flag = false;
        try {
            if (clazz != null && list != null && !list.isEmpty()) {
              getDaoSession().getDao(clazz).deleteInTx(list);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除所有对象
     *
     * @param
     * @return
     */
    public synchronized <T> boolean deleteAll(Class<T> clazz) {
        boolean flag = false;
        try {
            if (clazz != null) {
               getDaoSession().deleteAll(clazz);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 更新某一个对象
     * 由唯一id决定
     *
     * @param
     * @return
     */
    public synchronized <T> boolean update(T t) {
        boolean flag = false;
        try {
            if (t != null) {
               getDaoSession().update(t);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 更新多个对象
     * 由唯一id决定
     *
     * @param
     * @return
     */
    public synchronized <T> boolean updateList(Class clazz, List<T> list) {
        boolean flag = false;
        try {
            if (clazz != null && list != null && !list.isEmpty()) {
               getDaoSession().getDao(clazz).updateInTx(list);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 按照主键返回单行记录(根据id查询相应对象)
     *
     * @param key
     * @return
     */
    public synchronized <T> T load(Class<T> clazz, long key) {
        try {
            if (clazz != null) {
                return getDaoSession().load(clazz, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 返回多行记录(=queryAll)
     *
     * @return
     */
    public synchronized <T> List<T> loadAll(Class<T> clazz) {
        try {
            if (clazz != null) {
                return getDaoSession().loadAll(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询所有对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized <T> List<T> queryAll(Class<T> clazz) {
        try {
            QueryBuilder<T> queryBuilder = null;
            if (clazz != null) {
                if (queryBuilder == null) {
                    queryBuilder = getDaoSession().queryBuilder(clazz);
                }
                return queryBuilder.build().forCurrentThread().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询所有对象(有排序)
     * 查询排序
     *
     * @param <T>
     * @param clazz
     * @param isAsc      true 为升序(小-->大)
     * @param properties
     * @return
     */
    public synchronized <T> List<T> queryAll(Class<T> clazz, boolean isAsc, Property properties) {
        try {
            QueryBuilder<T> queryBuilder = null;
            if (clazz != null) {
                if (queryBuilder == null) {
                    queryBuilder = getDaoSession().queryBuilder(clazz);
                }
                if (isAsc == true) {
                    return queryBuilder.orderAsc(properties).build().forCurrentThread().list();
                } else if (isAsc == false) {
                    return queryBuilder.orderDesc(properties).build().forCurrentThread().list();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 按条件查
     * DBUtils.queryWhere(User.class, UserDao.Properties.Name.eq("tst"));
     *
     * @param clazz
     * @param cond
     * @param <T>
     * @return
     */
    public synchronized <T> List<T> queryWhere(Class<T> clazz, WhereCondition cond, WhereCondition... condMore) {
        try {
            QueryBuilder<T> queryBuilder = null;
            if (clazz != null && cond != null) {
                if (queryBuilder == null) {
                    queryBuilder = getDaoSession().queryBuilder(clazz);
                }
                return queryBuilder.where(cond, condMore).build().forCurrentThread().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 按条件查询(有排序)
     * DBUtils.queryWhere(User.class,UserDao.Properties.Size.ge(5),true, UserDao.Properties.Id);
     * @param clazz
     * @param cond
     * @param isAsc true 为升序(小-->大)
     * @param <T>
     * @return
     */
    public synchronized <T> List<T> queryWhere(Class<T> clazz, WhereCondition cond, boolean isAsc, Property... properties) {
        try {
            QueryBuilder<T> queryBuilder = null;

            if (clazz != null && cond != null) {
                if (queryBuilder == null) {
                    queryBuilder = getDaoSession().queryBuilder(clazz);
                }
                if (isAsc == true) {
                    return queryBuilder.where(cond).orderAsc(properties).build().forCurrentThread().list();
                } else if (isAsc == false) {
                    return queryBuilder.where(cond).orderDesc(properties).build().forCurrentThread().list();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 按条件查询
     * DBUtils.queryWhereOr(User.class,UserDao.Properties.Size.ge(5),UserDao.Properties.Identity.eq("3"),true, UserDao.Properties.Id);
     *
     * @param clazz
     * @param cond1
     * @param cond2
     * @param <T>
     * @return
     */
    public synchronized <T> List<T> queryWhereOr(Class<T> clazz, WhereCondition cond1, WhereCondition cond2, WhereCondition... condMore) {
        try {
            QueryBuilder<T> queryBuilder = null;

            if (clazz != null && cond1 != null && cond2 != null) {
                if (queryBuilder == null) {
                    queryBuilder = getDaoSession().queryBuilder(clazz);
                }

                return queryBuilder.whereOr(cond1, cond2, condMore).build().forCurrentThread().list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 按条件查询(有排序)
     *
     * @param clazz
     * @param cond1 (cond1||cond2)
     * @param cond2
     * @param isAsc true 为升序
     * @param <T>
     * @return
     */
    public synchronized <T> List<T> queryWhereOr(Class<T> clazz, WhereCondition cond1, WhereCondition cond2, boolean isAsc, Property... properties) {
        try {
            QueryBuilder<T> queryBuilder = null;
            if (clazz != null && cond1 != null && cond2 != null) {
                if (queryBuilder == null) {
                    queryBuilder = getDaoSession().queryBuilder(clazz);
                }
                if (isAsc == true) {
                    return queryBuilder.whereOr(cond1, cond2).orderAsc(properties).build().forCurrentThread().list();
                } else if (isAsc == false) {
                    return queryBuilder.whereOr(cond1, cond2).orderDesc(properties).build().forCurrentThread().list();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * =========================================== Rx ===============================================
     */
    public synchronized <T> RxDao<T, Long> getRxDao(Class<T> clazz) {
        try {
            RxDao<T, Long> LongRxDao = null;
            if (clazz != null) {
                LongRxDao = (RxDao<T, Long>) getDaoSession().getDao(clazz).rx();
            }
            return LongRxDao;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public synchronized <T> void Rxinsert(Class<T> clazz, T t) {
//        getRxDao(clazz).insertInTx(t);
//    }

    /**
     * 实体类更新时调用
     */
    public void clear() {
        getDaoSession().clear();
    }
}
