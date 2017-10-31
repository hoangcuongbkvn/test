package teamlab.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import teamlab.model.entity.Page;

public interface PageDao extends JpaRepository<Page, Integer> {

    @Query("select p from Page p where p.title like ?1%")
    public List<Page> findPageByTitle(String title);
}