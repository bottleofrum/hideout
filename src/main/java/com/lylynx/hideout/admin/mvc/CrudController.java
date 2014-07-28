package com.lylynx.hideout.admin.mvc;

import com.lylynx.hideout.config.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

public abstract class CrudController<T> {

    private MongoRepository<T, String> repository;

    public CrudController(final MongoRepository<T, String> repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "", produces = Constants.MIME_TYPE_JSON)
    @ResponseBody
    public Page<T> getAll(@RequestParam int page, @RequestParam int size) {
        return repository.findAll(new PageRequest(page,size));
    }

    @RequestMapping(value = "", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.POST,
            consumes = Constants.MIME_TYPE_JSON)
    public void create(T entity){
        repository.save(entity);
    }

    @RequestMapping(value = "/{id}", produces = Constants.MIME_TYPE_JSON)
    @ResponseBody
    public T get(@PathVariable String id){
        return repository.findOne(id);
    }

    @RequestMapping(value = "/{id}", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.PUT,
            consumes = Constants.MIME_TYPE_JSON)
    public void update(@PathVariable String id, T entity){
        repository.save(entity);
    }

    @RequestMapping(value = "/{id}", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.DELETE)
    public void delete(@PathVariable String id){
        repository.delete(id);
    }

}
