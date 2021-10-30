package com.audience.booking.server.service;

import com.audience.booking.server.dao.TemplatesDAO;
import com.audience.booking.server.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TemplateDataService {

    @Autowired
    private TemplatesDAO TemplatesCrudRepository;

    @Transactional
    public List<Template> getAllTemplates() {
        return (List<Template>) TemplatesCrudRepository.findAll();
    }

    @Transactional
    public void saveTemplates(Template template) {
        TemplatesCrudRepository.save(template);
    }

    @Transactional
    public void deleteTemplates(int id) {
        TemplatesCrudRepository.deleteById(id);
    }

    @Transactional
    public Template getTemplates(int id) {
        return TemplatesCrudRepository.findById(id).get();
    }

}
