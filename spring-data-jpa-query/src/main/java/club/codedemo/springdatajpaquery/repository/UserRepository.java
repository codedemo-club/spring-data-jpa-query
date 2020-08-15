package club.codedemo.springdatajpaquery.repository;

import club.codedemo.springdatajpaquery.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * 9.3 使用 UserRepositoryCustom 扩展了用户仓库接口
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    /**
     * 2.1 JPQL 查询
     * 方法作用：获取所有激活用户
     * @return 所有激活用户的集合
     */
    @Query("SELECT u FROM User u WHERE u.status = 1")
    Collection<User> findAllActiveUsersUsingJPQL();

    /**
     * 2.2 Native 原生查询
     * 方法作用： 获取所有激活用户
     * @return 所有激活用户的集合
     */
    @Query(value = "SELECT * FROM USER u WHERE u.status = 1", nativeQuery = true)
    Collection<User> findAllActiveUsersUsingNative();

    /**
     * 3.2 JPQL Sort排序
     * 方法作用：获取全部用户，并按传入的Sort进行排序
     * @return 所有用户的List
     */
    @Query(value = "SELECT u FROM User u")
    List<User> findAllUsersSortUsingJPQL(Sort sort);

    /**
     * 3.3 Native Sort排序
     * 方法作用：获取全部用户，并按传入的Sort进行排序
     * @return 所有用户的List
     */
    // 这是一个错误写法的示例，如果想查看运行结果，请取消注释
    // @Query(value = "SELECT * FROM USERS u WHERE u.status = 1", nativeQuery = true)
    // List<User> findAllUsersSortUsingNative(Sort sort);

    /**
     * 4.1 JPQL 分页
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     * @return 分页用户Page
     */
    @Query(value = "SELECT u FROM User u ORDER BY id")
    Page<User> findAllUsersWithPaginationUsingJPQL(Pageable pageable);

    /**
     * 4.2 Native 分页
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     * @return 分页用户Page
     */
    @Query(value = "SELECT * FROM User ORDER BY id", countQuery = "SELECT count(*) FROM User", nativeQuery = true)
    Page<User> findAllUsersWithPaginationUsingNative(Pageable pageable);

    /**
     * 4.3 Native 2.0.4之前的Spring Data JPA版本
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     * @return 分页用户Page
     */
    @Query(value = "SELECT * FROM User ORDER BY id \n-- #pageable\n",
            countQuery = "SELECT count(*) FROM User", nativeQuery = true)
    Page<User> findAllUsersWithPaginationUsingNativeBeforeJPA2_0_4(Pageable pageable);

    /**
     * 5.1 JPQL 索引参数查询 单参数
     * 方法作用： 根据条件Status查询用户
     * 要点：索引参数模式中，查询条件的顺序和方法参数的顺序相对应
     * @return 状态 == 参数 的所有用户的列表的列表
     */
    @Query("SELECT u FROM User u WHERE u.status = ?1")
    List<User> findUserByStatusUsingJPQL(Integer status);

    /**
     * 5.1 JPQL 索引参数查询 多参数
     * 方法作用： 根据条件Status和Name查询用户
     * 要点：索引参数模式中，查询条件的顺序和方法参数的顺序相对应
     * @return 状态 == 参数1， 姓名 == 参数2 的所有用户的列表
     */
    @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
    List<User> findUserByStatusAndNameUsingJPQL(Integer status, String name);

    /**
     * 5.2 Native 索引参数查询 单参数
     * 用法和JPQL中一致
     * @return 状态 == 参数 的所有用户的列表
     */
    @Query(value = "SELECT * FROM User u WHERE u.status = ?1", nativeQuery = true)
    List<User> findUserByStatusUsingNative(Integer status);

    /**
     * 6.1 JPQL 命名参数查询 多参数
     * 方法作用：根据条件Status和Name查询用户
     * 要点：命名参数模式中，查询条件的名称和Param参数的名称相对应
     * @return 状态 == 参数1， 姓名 == 参数2 的所有用户的列表
     */
    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    List<User> findUserByStatusAndNameNamedParamsUsingJPQL(@Param("status") Integer status, @Param("name") String name);

    /**
     * 6.1 上个方法的另一种写法
     * 注意：只要 Param中的字符串 和 查询条件 对应即可
     * 不需要 传入的变量 和 查询条件 对应
     * @return 状态 == 参数1， 姓名 == 参数2 的所有用户的列表
     */
    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
    List<User> findUserByUserStatusAndUserNameNamedParamsUsingJPQL(@Param("status") Integer userStatus,
                                                                   @Param("name") String userName);

    /**
     * 6.2 Native 命名参数查询 多参数
     * 方法作用：根据条件Status和Name查询用户
     * 要点：命名参数模式中，查询条件的名称和Param参数的名称相对应
     * @return 状态 == 参数1， 姓名 == 参数2 的所有用户的列表
     */
    @Query(value = "SELECT * FROM User u WHERE u.status = :status and u.name = :name", nativeQuery = true)
    List<User> findUserByStatusAndNameNamedParamsUsingNative(@Param("status") Integer status,
                                                             @Param("name") String name);

    /**
     * 7 JPQL 集合参数查询
     * 方法作用：传入一个姓名集合，查询数据表中属于给定集合的所有用户的列表
     * @return 姓名包含在集合中的所有用户的列表的列表
     */
    @Query(value = "SELECT u FROM User u WHERE u.name IN :names")
    List<User> findUserByNameListUsingJPQL(@Param("names") Collection<String> names);

    /**
     * 8.1 JPQL 执行更新操作
     * 方法作用：按 姓名 查找，更新用户的 状态
     */
    @Modifying
    @Query("update User u set u.status = :status where u.name = :name")
    int updateUserSetStatusForNameUsingJPQL(@Param("status") Integer status, @Param("name") String name);

    /**
     * 8.2 Native 执行更新操作
     * 方法作用：按 姓名 查找，更新用户的 状态
     */
    @Modifying
    @Query(value = "update User u set u.status = ? where u.name = ?", nativeQuery = true)
    int updateUserSetStatusForNameUsingNative(Integer status, String name);

    /**
     * 8.3 Native 执行插入操作
     * 方法作用：插入新的User记录
     * 注意：插入操作必须使用Native
     */
    @Modifying
    @Query(value = "insert into User (name, status, email) values (:name, :status, :email)", nativeQuery = true)
    void insertUserUsingNative(@Param("name") String name, @Param("status") Integer status, @Param("email") String email);

}
