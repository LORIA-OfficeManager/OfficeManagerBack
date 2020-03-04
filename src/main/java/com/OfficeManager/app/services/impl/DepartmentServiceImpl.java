package com.OfficeManager.app.services.impl;

import com.OfficeManager.app.daos.IDepartmentDao;
import com.OfficeManager.app.daos.ITeamDao;
import com.OfficeManager.app.entities.Department;
import com.OfficeManager.app.entities.Team;
import com.OfficeManager.app.services.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {

    public final static String DEFAULT_DEP = "Loria";

    @Autowired
    private IDepartmentDao departmentDao;

    // à supprimer plus tard cf (*)
    @Autowired
    TeamServiceImpl teamService;

    @Override
    public List<Department> fetchAll() {
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> findById(int id) {
        return departmentDao.findById(id);
    }

    @Override
    public Department findByName(String name) {
        return departmentDao.findByName(name);
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentDao.save(department);
    }

    @Override
    public void deleteById(Integer id){
        departmentDao.deleteById(id);
    }

    @Override
    public void swtichTeamToDefaultDepartment(int id) {
        // (*) A lancer au startup de l'app plus tard //
        Department Dep = this.findByName(DEFAULT_DEP);
        if (Dep == null) {
            Department defaultDep = new Department(DEFAULT_DEP);
            Team team = new Team(DEFAULT_DEP);
            team.setName(DEFAULT_DEP);
            teamService.saveTeam(team);
            this.saveDepartment(defaultDep);
        }
        // ------------------------------------- //
        departmentDao.switcTeamToDefaultDepartment(id, DEFAULT_DEP);
    }

    @Override
    public boolean isAuthorisedName(String name) {
        return !departmentDao.existsByName(name) && !name.equals(DEFAULT_DEP);
    }
}
