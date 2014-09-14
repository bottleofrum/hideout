package com.lylynx.hideout.admin.mvc;

import com.lylynx.hideout.admin.mvc.error.ErrorsBuilder;
import com.lylynx.hideout.config.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class CrudController<T> extends RestControllerSupport {

    private MongoRepository<T, String> repository;

    public CrudController(ErrorsBuilder errorsBuilder, final MongoRepository<T, String> repository) {
        super(errorsBuilder);
        this.repository = repository;
    }

    @RequestMapping(value = "", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.GET)
    @ResponseBody
    public Page<T> getAll(@RequestParam int page, @RequestParam int size) {
        return repository.findAll(new PageRequest(page, size));
    }

    @RequestMapping(value = "", consumes = Constants.MIME_TYPE_JSON, method = RequestMethod.POST)
    public ResponseEntity create(@Valid @RequestBody T entity) {
        repository.save(entity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.GET)
    @ResponseBody
    public T get(@PathVariable String id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/{id}", consumes = Constants.MIME_TYPE_JSON, method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @Valid @RequestBody T entity) {
        repository.save(entity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        repository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    protected MongoRepository<T, String> getRepository() {
        return repository;
    }
}
