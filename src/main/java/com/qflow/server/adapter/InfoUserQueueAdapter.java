package com.qflow.server.adapter;

import com.qflow.server.domain.repository.dto.InfoUserQueueDB;
import com.qflow.server.domain.repository.dto.QueueUserDB;
import com.qflow.server.entity.InfoUserQueue;
import com.qflow.server.entity.QueueUser;

public class InfoUserQueueAdapter {

    public InfoUserQueueAdapter() {
    }

    public InfoUserQueue infoUserQueueDBToInfoUserUser(InfoUserQueueDB infoUserQueueDB) {
        return InfoUserQueue.InfoUserQueueBuilder.anInfoUserQueue()
                .withId(infoUserQueueDB.getId())
                .withId_queue(infoUserQueueDB.getIdQueue())
                .withId_user(infoUserQueueDB.getIdUser())
                .withDate_access(infoUserQueueDB.getDateAccess())
                .withDate_success(infoUserQueueDB.getDateSuccess())
                .withIs_rate(infoUserQueueDB.getIsRate())
                .withUnattended(infoUserQueueDB.getUnattended())
                .build();
    }

    public InfoUserQueueDB infoUserQueueToInfoUserQueueDB(InfoUserQueue infoUserQueue){
        return InfoUserQueueDB.InfoUserQueueDBBuilder.anInfoUserQueueDB()
                .withId(infoUserQueue.getId())
                .withIdQueue(infoUserQueue.getId_queue())
                .withIdUser(infoUserQueue.getId_user())
                .withDateAccess(infoUserQueue.getDate_access())
                .withDateSuccess(infoUserQueue.getDate_success())
                .withIsRate(infoUserQueue.getIs_rate())
                .withUnattended(infoUserQueue.isUnattended())
                .build();
    }

}
