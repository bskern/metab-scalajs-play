package css

import scalacss.Defaults._

object GlobalStyle extends StyleSheet.Inline {

  import dsl._

  style(unsafeRoot("body")(
    margin.`0`,
    padding.`0`,
    fontSize(14.px),
    fontFamily := "Helvetica Neue,Helvetica,Arial,sans-serif;"
  ))


  def styleWrap(classNames: String*) = style(addClassNames(classNames: _*))

  val row = styleWrap("row")

  val bootStrapColDomain = Domain.ofRange(0 to 12)
  val col = styleF(bootStrapColDomain)(i => addClassName(s"col-md-$i"))

  //article
  val title = style(
    fontFamily :=! "Verdana, Geneva, sans-serif",
    fontSize(10.pt),
    color(c"#828282")
  )
  val content = style(
    backgroundColor(c"#f6f6ef"),
    textAlign.left,
    paddingLeft(5.px))

  //header
  val hn = style(
    backgroundColor(c"#ff6600"),
    fontWeight.bold,
    fontSize(10.pt),
    fontFamily :=! "Verdana, Geneva, sans-serif"
  )
  val reddit = style(
    backgroundColor(c"#cee3f8"),
    borderBottomColor(c"#5f99cf"),
    borderBottomWidth(1.px),
    borderBottomStyle.solid,
    fontWeight.bold,
    fontFamily :=! "Verdana, Geneva, sans-serif",
    fontSize(10.pt)
  )
  val siteSpecific = styleF.bool(isReddit => styleS(
    if (isReddit) reddit else hn
  ))
  //sitecontainer
  val container = styleWrap("container-fluid")
  val app = style(textAlign.center)


  //weathercontainer
  val navbar = styleWrap("navbar", "navbar-inverse")
  val navbarBrand = styleWrap("navbar-brand")
  val navbarHeader = styleWrap("navbar-header")
  val navbarList = styleWrap("nav", "navbar-nav", "navbar-right")

}
