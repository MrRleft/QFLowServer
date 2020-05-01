package com.qflow.server.controller;

import com.qflow.server.adapter.QueueAdapter;
import com.qflow.server.controller.dto.QueuePost;
import com.qflow.server.entity.Queue;
import com.qflow.server.usecase.queues.CreateQueue;
import com.qflow.server.usecase.queues.GetQueuesByUserId;
import com.qflow.server.usecase.queues.GetQueueByQueueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("qflow/queues")
public class QueuesController {

    private final GetQueuesByUserId getQueuesByUserId;
    private final GetQueueByQueueId getQueueByQueueId;
    private final CreateQueue createQueue;
    private final QueueAdapter queueAdapter;



    public QueuesController(@Autowired final GetQueuesByUserId getQueuesByUserId,
                            @Autowired final GetQueueByQueueId getQueueByQueueId,
                            @Autowired final CreateQueue createQueue,
                            @Autowired final QueueAdapter queueAdapter) {
        this.getQueuesByUserId = getQueuesByUserId;
        this.getQueueByQueueId = getQueueByQueueId;
        this.createQueue = createQueue;
        this.queueAdapter = queueAdapter;
    }

    //TODO Change mapping?
    //TODO Receive token and convert to idUser
    @GetMapping("/byIdUser/{idUser}")
    public ResponseEntity<List<Queue>> getQueuesByUserId(
            @PathVariable("idUser") final int idUser,
            @RequestHeader(required = false) String expand,
            @RequestHeader(required = false) boolean locked) {
        return new ResponseEntity<>(
                this.getQueuesByUserId.execute(expand, idUser, locked), HttpStatus.OK);
    }

    @GetMapping("/byIdQueue/{idQueue}")
    public ResponseEntity<Queue> getQueueByQueueId(@PathVariable("idQueue") final int idQueue) {
        return new ResponseEntity<>(
                this.getQueueByQueueId.execute(idQueue), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Queue> postQueue(
            @RequestBody @Valid final QueuePost queuePost,
            @RequestHeader @Valid final String token
    ) {
        return new ResponseEntity<Queue>(
                this.createQueue.execute(
                        this.queueAdapter.queuePostToQueue(queuePost), token
                ), HttpStatus.CREATED);
    }
}

/*
@GetMapping(produces = "application/json")
    public ResponseEntity<EntityModel<?>> getApiList(
            @RequestParam(value = "_limit", required = false, defaultValue = "9") final int limit,
            @RequestParam(value = "_offset", required = false, defaultValue = "0") final int offset,
            @RequestParam(value = "_sort", required = false, defaultValue = "+title") final String sort,
            @RequestParam(value = "_search", required = false, defaultValue = "") final String search,
            @RequestParam(value = "_expand", required = false, defaultValue = "") final String expand,
            @RequestParam(value = "entity", required = false, defaultValue = "-1") final int entityAL,
            @RequestHeader(value = "x-organization-id", required = false, defaultValue = "") final String organizationIdHeader
            ) {

        final String organizationConsumerId = "".equals(organizationIdHeader) ? null : organizationIdHeader;

        final String sortFilter = org.owasp.encoder.Encode.forJava(sort);
        final String searchFilter = org.owasp.encoder.Encode.forJava(search);
        final String expandFilter = org.owasp.encoder.Encode.forJava(expand);

        // Check Request Params
        utilsController.checkParamsOfGetApiList(limit, expandFilter, 0, 50, Arrays.asList(ALL, PRODUCTS));

        final List<ApiWithALProductsAndApiSpec> allApiList =
                this.getApiList.execute(limit, offset, sortFilter, searchFilter,
                        expandFilter, organizationConsumerId, entityAL);

        // Get total APIs: _count
        final int totalApis = apiService.getCountApis(searchFilter, organizationConsumerId);
        final APIList apiList = new APIList(generateApisEmbedded(allApiList, expandFilter), totalApis);

        final EntityModel<?> response = new EntityModel<>(apiList);

        // Hateoas
        utilsController.generatePaginationLinksHateoas(response, offset, limit, totalApis,
                utilsController.getCurrentURI());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
 */
