package com.epam.esm.persistence;

import com.epam.esm.criteria_info.UserCriteriaInfo;
import com.epam.esm.entity.User;

public interface UserDAO extends CrudDAO<User, Long, UserCriteriaInfo>{

    long getCount();
}
