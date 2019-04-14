package com.primeton.eos.governor.agent.repo.dao;

import com.primeton.eos.governor.agent.repo.model.AppNodePO;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public abstract interface AppNodeRepository
        extends JpaRepository<AppNodePO, String>
{
    public abstract AppNodePO findByCodeAndDelFlag(String paramString, boolean paramBoolean);

}
