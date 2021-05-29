package com.yunseong.core.member.service;

import com.yunseong.core.member.domain.Member;
import com.yunseong.core.member.domain.MemberRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepositoryStub implements MemberRepository {

    private List<Member> members = new ArrayList<>();

    private void add(Member entity) {
        try {
            this.members.add(entity);
            Member member = this.members.get(this.members.size()-1);
            Field field = member.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(member, Long.valueOf(this.members.size()));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public List<Member> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Member> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Member entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Member> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Member> S save(S entity) {
        add(entity);
        return entity;
    }

    @Override
    public <S extends Member> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long aLong) {
        return Optional.of(this.members.get(Math.toIntExact(aLong-1)));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Member> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Member> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Member getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Member> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Member> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Member> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return this.members.stream().filter(m -> m.getEmail().equals(email)).findFirst();
    }
}
