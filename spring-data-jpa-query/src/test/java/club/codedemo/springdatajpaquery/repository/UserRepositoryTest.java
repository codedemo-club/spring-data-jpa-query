package club.codedemo.springdatajpaquery.repository;

import club.codedemo.springdatajpaquery.entity.User;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    /**
     * 2.1 JPQL 查询
     * 方法作用：获取所有激活用户
     */
    @Test
    @Transactional
    void findAllActiveUsersUsingJPQL() {
        Collection<User> users = this.userRepository.findAllActiveUsersUsingJPQL();
        Assertions.assertEquals(users.size(), 2);
    }

    /**
     * 2.2 Native 原生查询
     * 方法作用： 获取所有激活用户
     */
    @Test
    @Transactional
    void findAllActiveUsersUsingNative() {
        Collection<User> users = this.userRepository.findAllActiveUsersUsingNative();
        Assertions.assertEquals(users.size(), 2);
    }

    /**
     * 3.1 使用JPA内置方法 进行Sort()排序操作
     * 方法作用： 按对象的"名称"属性音序排序
     * 注：new Sort()无法再实例化，因此改为Sort.by()
     */
    @Test
    @Transactional
    void findAllUsersSortByObjectPropertiesNameUsingJpaMethods() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhangsan");
        Assertions.assertEquals(users.get(4).getName(), "zhaoliu");
    }

    /**
     * 3.1 使用JPA内置方法 进行Sort()排序操作
     * 方法作用： 按字符串"Name"的长度排序（非对象属性）
     * 注：new Sort()无法再实例化，因此改为Sort.by()
     */
    @Test
    @Transactional
    void findAllSortByPropertyNameUsingJpaMethods() {

        List<User> users = userRepository.findAll(Sort.by("LENGTH(name)"));
        // 测试不会通过，会出现异常，但我们仍断言结果，Name长度依次递增
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(4).getName(), "zhangsan");
    }

    /**
     * 3.2 JPQL 进行Sort()排序操作
     * 方法作用： 按字符串"名称"的音序排序(非对象属性)
     */
    @Test
    @Transactional
    void findAllUsersSortByObjectPropertiesNameUsingJPQL() {
        List<User> users = userRepository.findAllUsersSortUsingJPQL(Sort.by("name"));
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhangsan");
        Assertions.assertEquals(users.get(4).getName(), "zhaoliu");
    }

    /**
     * 3.2 JPQL 进行Sort()排序操作
     * 方法作用： 按字符串"名称"的长度排序(非对象属性)(使用Unsafe)
     */
    @Test
    @Transactional
    void findAllUsersSortByPropertyNameUnsafeUsingJPQL() {
        List<User> users = userRepository.findAllUsersSortUsingJPQL(JpaSort.unsafe("LENGTH(name)"));
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(4).getName(), "zhangsan");
    }

    /**
     * 3.2 JPQL 进行Sort()排序操作
     * 方法作用： 按字符串"名称"的长度排序(非对象属性)(不使用Unsafe)
     */
    @Test
    @Transactional
    void findAllUsersSortByPropertyNameUsingJPQL() {
        List<User> users = userRepository.findAllUsersSortUsingJPQL(Sort.by("LENGTH(name)"));
        // 测试不会通过，会出现异常，但我们仍断言结果，Name长度依次递增
        Assertions.assertEquals(users.size(), 5);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "sunqi");
        Assertions.assertEquals(users.get(2).getName(), "wangwu");
        Assertions.assertEquals(users.get(3).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(4).getName(), "zhangsan");
    }

    /**
     * 3.3 Native原生查询 进行Sort()排序操作
     * 方法作用： 按对象的"Name"属性音序排序
     */
//    @Test
//    @Transactional
//    void findAllUsersSortByObjectPropertiesNameUsingNative() {
//        List<User> users = userRepository.findAllUsersSortUsingNative(Sort.by("name"));
//        // 测试不会通过，会出现异常，但我们仍断言结果，Name按音序依次递增
//        Assertions.assertEquals(users.size(), 5);
//        Assertions.assertEquals(users.get(0).getName(), "lisi");
//        Assertions.assertEquals(users.get(1).getName(), "sunqi");
//        Assertions.assertEquals(users.get(2).getName(), "wangwu");
//        Assertions.assertEquals(users.get(3).getName(), "zhangsan");
//        Assertions.assertEquals(users.get(4).getName(), "zhaoliu");
//    }

    /**
     * 4.1 JPQL 分页
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     */
    @Test
    @Transactional
    void findAllUsersWithPaginationUsingJPQL() {
        Pageable page = PageRequest.of(0, 2);
        Page<User> users = userRepository.findAllUsersWithPaginationUsingJPQL(page);
        Assertions.assertEquals(users.getSize(), 2);
        Assertions.assertEquals(users.getContent().get(0).getName(), "zhangsan");
        Assertions.assertEquals(users.getContent().get(1).getName(), "lisi");
    }

    /**
     * 4.2 Native 分页
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     */
    @Test
    @Transactional
    void findAllUsersWithPaginationUsingNative() {
        Pageable page = PageRequest.of(0, 2);
        Page<User> users = userRepository.findAllUsersWithPaginationUsingNative(page);
        Assertions.assertEquals(users.getSize(), 2);
        Assertions.assertEquals(users.getContent().get(0).getName(), "zhangsan");
        Assertions.assertEquals(users.getContent().get(1).getName(), "lisi");
    }

    /**
     * 4.3 Native 分页 适用于2.0.4之前的Spring Data JPA版本
     * 方法作用：获取全部用户，并按传入的Pageable进行分页
     */
    @Test
    @Transactional
    void findAllUsersWithPaginationUsingNativeBeforeJPA2_0_4() {
        Pageable page = PageRequest.of(0, 2);
        Page<User> users = userRepository.findAllUsersWithPaginationUsingNativeBeforeJPA2_0_4(page);
        Assertions.assertEquals(users.getSize(), 2);
        Assertions.assertEquals(users.getContent().get(0).getName(), "zhangsan");
        Assertions.assertEquals(users.getContent().get(1).getName(), "lisi");
    }

    /**
     * 5.1 JPQL 索引参数查询 单参数
     * 方法作用： 根据条件Status查询用户
     */
    @Test
    @Transactional
    void findUserByStatusUsingJPQL() {
        List<User> users = userRepository.findUserByStatusUsingJPQL(1);
        Assertions.assertEquals(users.size(),2);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
        Assertions.assertEquals(users.get(1).getName(), "wangwu");

        users = userRepository.findUserByStatusUsingJPQL(0);
        Assertions.assertEquals(users.size(),3);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(2).getName(), "sunqi");
    }

    /**
     * 5.1 JPQL 索引参数查询 多参数
     * 方法作用： 根据条件Status和Name查询用户
     */
    @Test
    @Transactional
    void findUserByStatusAndNameUsingJPQL() {
        List<User> users = userRepository.findUserByStatusAndNameUsingJPQL(1, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");

        users = userRepository.findUserByStatusAndNameUsingJPQL(0, "zhangsan");
        Assertions.assertEquals(users.size(), 0);

        users = userRepository.findUserByStatusAndNameUsingJPQL(0, "lisi");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
    }

    /**
     * 5.2 Native 索引参数查询 单参数
     * 和JPQL的用法一致
     */
    @Test
    @Transactional
    void findUserByStatusUsingNative() {
        List<User> users = userRepository.findUserByStatusUsingNative(1);
        Assertions.assertEquals(users.size(),2);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
        Assertions.assertEquals(users.get(1).getName(), "wangwu");

        users = userRepository.findUserByStatusUsingJPQL(0);
        Assertions.assertEquals(users.size(),3);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
        Assertions.assertEquals(users.get(1).getName(), "zhaoliu");
        Assertions.assertEquals(users.get(2).getName(), "sunqi");
    }

    /**
     * 6.1 JPQL 命名参数查询 多参数
     * 方法作用：根据条件Status和Name查询用户
     */
    @Test
    @Transactional
    void findUserByStatusAndNameNamedParamsUsingJPQL() {
        List<User> users = userRepository.findUserByStatusAndNameNamedParamsUsingJPQL(1, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");

        users = userRepository.findUserByStatusAndNameNamedParamsUsingJPQL(0, "zhangsan");
        Assertions.assertEquals(users.size(), 0);

        users = userRepository.findUserByStatusAndNameNamedParamsUsingJPQL(0, "lisi");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
    }

    /**
     * 6.1 JPQL 命名参数查询 多参数 另一种写法
     * 注意：只要 Param中的字符串 和 查询条件 对应即可
     * 不需要 传入的变量 和 查询条件 对应
     */
    @Test
    @Transactional
    void findUserByUserStatusAndUserNameNamedParamsUsingJPQL() {
        List<User> users = userRepository.findUserByUserStatusAndUserNameNamedParamsUsingJPQL(1, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");

        users = userRepository.findUserByUserStatusAndUserNameNamedParamsUsingJPQL(0, "zhangsan");
        Assertions.assertEquals(users.size(), 0);

        users = userRepository.findUserByUserStatusAndUserNameNamedParamsUsingJPQL(0, "lisi");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
    }

    /**
     * 6.2 Native 命名参数查询 多参数
     * 方法作用：根据条件Status和Name查询用户
     */
    @Test
    @Transactional
    void findUserByStatusAndNameNamedParamsUsingNative(){
        List<User> users = userRepository.findUserByStatusAndNameNamedParamsUsingNative(1, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");

        users = userRepository.findUserByStatusAndNameNamedParamsUsingNative(0, "zhangsan");
        Assertions.assertEquals(users.size(), 0);

        users = userRepository.findUserByStatusAndNameNamedParamsUsingNative(0, "lisi");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
    }

    /**
     * 7 JPQL 集合参数查询
     * 方法作用：传入一个姓名集合，查询数据表中属于给定集合的所有用户
     */
    @Test
    @Transactional
    void findUserByNameListUsingJPQL() {
        // 用空数组查询，什么也不返回
        List<String> names = Lists.newArrayList();
        List<User> users = userRepository.findUserByNameListUsingJPQL(names);
        Assertions.assertEquals(users.size(), 0);

        // 数组有一个无用数据
        names.add("liuba");
        users = userRepository.findUserByNameListUsingJPQL(names);
        Assertions.assertEquals(users.size(), 0);

        // 数组有一个有用数据和一个无用数据
        names.add("lisi");
        users = userRepository.findUserByNameListUsingJPQL(names);
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "lisi");
    }

    /**
     * 8.1 JPQL 执行更新操作
     * 方法作用：按 姓名 查找，更新用户的 状态
     */
    @Test
    @Transactional
    void updateUserSetStatusForNameUsingJPQL() {
        // 原始情况，断言存在一个状态为1的张三
        List<User> users = userRepository.findUserByStatusAndNameUsingJPQL(1, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
        // 执行更新操作
        userRepository.updateUserSetStatusForNameUsingJPQL(0, "zhangsan");
        // 断言更新后的情况，断言存在一个状态为0的张三
        users = userRepository.findUserByStatusAndNameUsingJPQL(0, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
    }

    /**
     * 8.2 Native 执行更新操作
     * 方法作用：按 姓名 查找，更新用户的 状态
     */
    @Test
    @Transactional
    void updateUserSetStatusForNameUsingNative() {
        // 原始情况，断言存在一个状态为1的张三
        List<User> users = userRepository.findUserByStatusAndNameUsingJPQL(1, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
        // 执行更新操作
        userRepository.updateUserSetStatusForNameUsingNative(0, "zhangsan");
        // 断言更新后的情况，断言存在一个状态为0的张三
        users = userRepository.findUserByStatusAndNameUsingJPQL(0, "zhangsan");
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
    }

    /**
     * 8.3 Native 执行插入操作
     * 方法作用：插入新的User记录
     * 注意：插入操作必须使用Native
     */
    @Test
    @Transactional
    void insertUserUsingNative() {
        // 断言初始情况，共5条数据
        List<User> users = userRepository.findAllUsersSortUsingJPQL(Sort.by("name"));
        Assertions.assertEquals(users.size(), 5);
        // 执行插入操作
        userRepository.insertUserUsingNative("liuba", 13, "123456@123456.com");
        // 断言插入后的结果，共6条数据
        users = userRepository.findAllUsersSortUsingJPQL(Sort.by("name"));
        Assertions.assertEquals(users.size(), 6);
        Assertions.assertEquals(users.get(0).getName(), "liuba");
    }

    /**
     * 9.4 调用动态查询方法
     * 方法作用: 集合查询，传入一个邮箱集合，查询数据表中属于给定集合的所有用户
     */
    @Transactional
    @Test
    void findUserByEmailsUsingCriteria() {
        Set<String> emails = new HashSet<>();
        // 空数组，断言结果为空
        List<User> users = userRepository.findUserByEmailsUsingCriteria(emails);
        Assertions.assertEquals(users.size(), 0);

        // 添加一个无用数据，断言结果仍为空
        emails.add("test@test.com");
        users = userRepository.findUserByEmailsUsingCriteria(emails);
        Assertions.assertEquals(users.size(), 0);

        // 数组包含一个有用数据和一个无用数据
        emails.add("123@123.com");
        users = userRepository.findUserByEmailsUsingCriteria(emails);
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "zhangsan");
    }
}