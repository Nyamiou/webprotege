package edu.stanford.bmir.protege.web.server.hierarchy;

import edu.stanford.bmir.protege.web.server.access.AccessManager;
import edu.stanford.bmir.protege.web.server.dispatch.AbstractHasProjectActionHandler;
import edu.stanford.bmir.protege.web.server.dispatch.ExecutionContext;
import edu.stanford.bmir.protege.web.shared.hierarchy.EntityHierarchyNode;
import edu.stanford.bmir.protege.web.shared.hierarchy.GetHierarchyPathsToRootAction;
import edu.stanford.bmir.protege.web.shared.hierarchy.GetHierarchyPathsToRootResult;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import edu.stanford.bmir.protege.web.shared.user.UserId;
import edu.stanford.protege.gwt.graphtree.shared.Path;
import edu.stanford.protege.gwt.graphtree.shared.graph.GraphNode;
import org.semanticweb.owlapi.model.OWLClass;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 28 Nov 2017
 */
public class GetHierarchyPathsToRootActionHandler extends AbstractHasProjectActionHandler<GetHierarchyPathsToRootAction, GetHierarchyPathsToRootResult> {

    @Nonnull
    private final ClassHierarchyProvider classHierarchyProvider;

    @Nonnull
    private final EntityHierarchyNodeRenderer renderer;

    @Inject
    public GetHierarchyPathsToRootActionHandler(@Nonnull AccessManager accessManager,
                                                @Nonnull ClassHierarchyProvider classHierarchyProvider,
                                                @Nonnull EntityHierarchyNodeRenderer renderer) {
        super(accessManager);
        this.classHierarchyProvider = classHierarchyProvider;
        this.renderer = renderer;
    }

    @Override
    public Class<GetHierarchyPathsToRootAction> getActionClass() {
        return GetHierarchyPathsToRootAction.class;
    }

    @Override
    public GetHierarchyPathsToRootResult execute(GetHierarchyPathsToRootAction action, ExecutionContext executionContext) {
        Set<List<OWLClass>> pathsToRoot = classHierarchyProvider.getPathsToRoot(action.getEntity().asOWLClass());
        List<Path<GraphNode<EntityHierarchyNode>>> result =
                pathsToRoot.stream()
                           .map(path -> {
                               List<GraphNode<EntityHierarchyNode>> nodePath = path.stream()
                                                                                   .map(cls -> toGraphNode(cls, action.getProjectId(), executionContext.getUserId()))
                                                                                   .collect(toList());
                               return new Path<>(nodePath);
                           }).collect(toList());
        return new GetHierarchyPathsToRootResult(result);
    }

    private GraphNode<EntityHierarchyNode> toGraphNode(OWLClass cls, ProjectId projectId, UserId userId) {
        return new GraphNode<>(
                renderer.render(cls, userId),
                classHierarchyProvider.getChildren(cls).isEmpty());
    }
}