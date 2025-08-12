package vn.ypp4.quanphan.repository.interf;

import vn.ypp4.quanphan.domain.Workspace;

import java.util.List;

public interface WorkspaceRepository {
    List<Workspace> findAccessibleWorkspacesByUserId(int userId);
}
