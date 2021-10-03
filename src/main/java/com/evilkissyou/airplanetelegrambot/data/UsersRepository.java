package com.evilkissyou.airplanetelegrambot.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
    boolean existsUserByChatId(Integer ChatId);
    User findByChatId(Integer ChatId);
}
