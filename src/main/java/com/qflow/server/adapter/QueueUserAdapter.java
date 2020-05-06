package com.qflow.server.adapter;

import com.qflow.server.domain.repository.dto.QueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import com.qflow.server.entity.Queue;
import com.qflow.server.entity.QueueUser;

public class QueueUserAdapter {

    public QueueUserAdapter() {
    }

    public QueueUser queueUserDBToQueueUser(QueueUserDB queueUserDB)
    {
        return QueueUser.QueueUserBuilder.aQueueUser()
                .withId(queueUserDB.getId())
                .withId_queue_qu_fk(queueUserDB.getIdQueue())
                .withId_user_qu_fk(queueUserDB.getIdUser())
                .withIs_active(queueUserDB.getActive())
                .withIs_admin(queueUserDB.getAdmin())
                .withPosition(queueUserDB.getPosition())
                .build();
    }

    public QueueUserDB queueUserToQueueUserDB(QueueUser queueUser){
        return QueueUserDB.QueueUserDBBuilder.aQueueUserDB()
                .withId(queueUser.getId())
                .withIdQueue(queueUser.getId_queue_qu_fk())
                .withIdUser(queueUser.getId_user_qu_fk())
                .withIsActive(queueUser.getIs_active())
                .withIsAdmin(queueUser.getIs_admin())
                .withPosition(queueUser.getPosition())
                .build();
    }
}
