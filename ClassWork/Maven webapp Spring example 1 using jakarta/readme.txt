Fix Project Facets

Right-click your project → Properties.

Go to Project Facets.

If you see:

Dynamic Web Module = 2.3 (old) → Change it to 6.0 (works with Tomcat 10.1).

Java = 17 (or higher).

If facets are locked:

Click "Further configuration available…"

Or edit the .settings/org.eclipse.wst.common.project.facet.core.xml file manually:

<faceted-project>
  <fixed facet="jst.java"/>
  <fixed facet="jst.web"/>
  <installed facet="jst.java" version="17"/>
  <installed facet="jst.web" version="6.0"/>
</faceted-project>
