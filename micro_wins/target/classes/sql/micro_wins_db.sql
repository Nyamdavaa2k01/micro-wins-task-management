/*show all databases*/
SHOW DATABASES
    ;
    /*if not exist micro_wins DB, create micro_wins DB*/
    -- drop database if exists micro_wins;
CREATE DATABASE IF NOT EXISTS micro_wins;
/*select DB from system*/
USE
    micro_wins;
    /*create table section. Checked if not exists on all created table.*/
    -- drop table if exists mw_user;  
CREATE TABLE IF NOT EXISTS mw_user(
    user_id INTEGER AUTO_INCREMENT,
    device_id INTEGER NOT NULL,
    device_name VARCHAR(100) NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    user_status INTEGER DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(user_id)
);
-- mw_user default users:
INSERT INTO mw_user(
    user_id,
    device_id,
    device_name,
    user_name,
    user_status
)
VALUES(11, 456, 'DEVICE-01', 'Bagaa', 1),(
    12,
    123456,
    'DEVICE-02',
    'Nyamaa',
    1
);
-- select * from mw_user;
-- drop table if exists mw_dict_type;
CREATE TABLE IF NOT EXISTS mw_dict_type(
    dt_type_no INTEGER AUTO_INCREMENT,
    dt_type_name VARCHAR(20) NOT NULL,
    dt_eng_name VARCHAR(20) NOT NULL,
    dt_type_value VARCHAR(30) NOT NULL,
    PRIMARY KEY(dt_type_no)
);
-- mw_dict_type table values:
INSERT INTO mw_dict_type(
    dt_type_name,
    dt_eng_name,
    dt_type_value
)
VALUES(
    'status',
    'status',
    'mw_dict_type.status'
),(
    'user_status',
    'user_status',
    'mw_dict_type.user_status'
),(
    'priority',
    'priority',
    'mw_dict_type.priority'
);
-- select * from mw_dict_type;
-- drop table if exists mw_dictionary;
CREATE TABLE IF NOT EXISTS mw_dictionary(
    dict_id INTEGER AUTO_INCREMENT,
    dict_type_no INTEGER NOT NULL,
    dict_name VARCHAR(30) NOT NULL,
    dict_value INTEGER NOT NULL,
    UNIQUE(dict_type_no, dict_value),
    PRIMARY KEY(dict_id),
    FOREIGN KEY(dict_type_no) REFERENCES mw_dict_type(dt_type_no) ON UPDATE CASCADE ON DELETE CASCADE
);
-- mw_dictionary table values:
INSERT INTO mw_dictionary(
    dict_type_no,
    dict_name,
    dict_value
)
VALUES(1, 'open', 1),(1, 'working', 2),(1, 'postponed', 3),(1, 'completed', 4),(2, 'active', 1),(2, 'inactive', 0),(3, 'urgent', 1),(3, 'high', 2),(3, 'medium', 3),(3, 'low', 4);
-- select * from mw_dictionary; 
-- drop table if exists mw_project;
CREATE TABLE IF NOT EXISTS mw_project(
    pro_id INTEGER AUTO_INCREMENT,
    pro_title VARCHAR(30),
    pro_owner INTEGER NOT NULL,
    pro_completion_percent FLOAT,
    pro_description VARCHAR(120),
    pro_status INTEGER,
    pro_start_date DATE NULL,
    pro_deadline DATE NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(pro_id),
    FOREIGN KEY(pro_owner) REFERENCES mw_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(pro_status) REFERENCES mw_dictionary(dict_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_project;
INSERT INTO mw_project(
    pro_id,
    pro_title,
    pro_owner,
    pro_completion_percent,
    pro_description,
    pro_status,
    pro_start_date,
    pro_deadline
)
VALUES(
    1,
    'inbox',
    11,
    0.0,
    '',
    1,
    NULL,
    NULL
),(
    2,
    'inbox',
    12,
    0.0,
    '',
    1,
    NULL,
    NULL
);
-- drop table if exists mw_task;
CREATE TABLE IF NOT EXISTS mw_task(
    task_id INTEGER AUTO_INCREMENT,
    task_title VARCHAR(30) NOT NULL,
    task_definition VARCHAR(200) NULL,
    task_priority INTEGER,
    task_status INTEGER,
    task_category VARCHAR(100),
    task_start_date DATE NULL,
    task_deadline DATE NULL,
    task_user_id INTEGER NOT NULL,
    task_pro_id INTEGER DEFAULT 1,
    task_pro_title VARCHAR(30) NULL DEFAULT 'inbox',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(task_id),
    FOREIGN KEY(task_priority) REFERENCES mw_dictionary(dict_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(task_status) REFERENCES mw_dictionary(dict_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(task_user_id) REFERENCES mw_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(task_pro_id) REFERENCES mw_project(pro_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_task;
-- drop table if exists mw_updated_task;
CREATE TABLE IF NOT EXISTS mw_updated_task(
    update_id INTEGER AUTO_INCREMENT,
    task_id INTEGER NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(update_id),
    FOREIGN KEY(task_id) REFERENCES mw_task(task_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_updated_task;
-- drop table if exists mw_task_result;
CREATE TABLE IF NOT EXISTS mw_task_result(
    res_id INTEGER AUTO_INCREMENT,
    task_id INTEGER NOT NULL,
    task_completion_percent FLOAT,
    task_completed_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(res_id),
    FOREIGN KEY(task_id) REFERENCES mw_task(task_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_task_result
-- drop table if exists mw_project_member;
CREATE TABLE IF NOT EXISTS mw_project_member(
    pro_mem_id INTEGER AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    pro_id INTEGER NOT NULL,
    user_role VARCHAR(10),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(pro_mem_id),
    FOREIGN KEY(pro_id) REFERENCES mw_project(pro_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(user_id) REFERENCES mw_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_project_member
-- drop table if exists mw_project_task;
CREATE TABLE IF NOT EXISTS mw_project_task(
    pro_task_id INTEGER AUTO_INCREMENT,
    pro_id INTEGER NOT NULL,
    handler_id INTEGER,
    pro_owner INTEGER NOT NULL,
    task_id INTEGER NOT NULL,
    task_status INTEGER NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(pro_task_id),
    FOREIGN KEY(pro_id) REFERENCES mw_project(pro_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(handler_id) REFERENCES mw_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(pro_owner) REFERENCES mw_project(pro_owner) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(task_id) REFERENCES mw_task(task_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(task_status) REFERENCES mw_dictionary(dict_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_project_task
-- drop table if exists mw_project_activity;
CREATE TABLE IF NOT EXISTS mw_project_activity(
    pro_act_id INTEGER AUTO_INCREMENT,
    pro_id INTEGER NOT NULL,
    handler_id INTEGER,
    pro_act_status INTEGER,
    task_id INTEGER NOT NULL,
    pro_act_comment VARCHAR(200),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(pro_act_id),
    FOREIGN KEY(pro_id) REFERENCES mw_project(pro_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(handler_id) REFERENCES mw_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(task_id) REFERENCES mw_task(task_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(pro_act_status) REFERENCES mw_dictionary(dict_id) ON UPDATE CASCADE ON DELETE CASCADE
);
-- select * from mw_project_activity