package org.vaadin.TinyMCE;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.internal.PendingJavaScriptInvocation;
import com.vaadin.flow.component.internal.UIInternals;
import com.vaadin.flow.internal.StateNode;

/**
 * @author Sergey.Tolstykh
 * @version 1.0
 *          Date 30.05.2022
 */

@Tag("tinymce-editor")
@NpmPackage(value = "@tinymce/tinymce-webcomponent",version = "2.0.0")
@JsModule("./src/tinymce-loader.js")
public class TinyMCEeditor extends CustomField<String> {
  private String id;
  private String elem;
  private String innerHTML;

  public TinyMCEeditor(String apikey, String innerHTML,String height, String menubar, String plugins, String toolbar, String contentStyle) {
    setApiKey(apikey != null ? apikey : "no-api-key");
    if (height != null)
      setEditorHeight(height);
    if (menubar != null)
      setMenubar(menubar);
    if (plugins != null)
      setPlugins(plugins);
    if (toolbar != null)
      setToolbar(toolbar);
    if (contentStyle != null)
      setContentStyle(contentStyle);
    if (innerHTML != null)
      this.innerHTML = innerHTML;
    getElement().setAttribute("id", "text-editor");
    id = getElement().getAttribute("id");
    elem = "document.getElementById('" + id + "')";
    addTextChangeListener();
  }

  private void setContentStyle(String s) {
    this.getElement().setAttribute( "content_style",
            s);
  }

  private void setToolbar(String args) {
    this.getElement().setAttribute( "toolbar", args);
  }

  private void setPlugins(String args) {
    this.getElement().setAttribute( "plugins",args);
  }

  private void setMenubar(String args) {
    this.getElement().setAttribute( "menubar", args.equals("false") ? "false" : args);
  }

  private void setEditorHeight(String height) {
    this.getElement().setAttribute( "height", height);
  }

  private void setApiKey(String apikey) {
    this.getElement().setAttribute("api-key", apikey);
  }

  @Override
  protected String generateModelValue() {
    return getElement().getText();
  }

  @Override
  protected void setPresentationValue(String s) {
    super.setValue(s);
  }

  @Override
  public String getValue() {
    return super.getValue();
  }

  @Override
  public void setValue(String value) {
    super.setValue(value);
  }



  private PendingJavaScriptInvocation getJavaScriptReturn(StateNode node, String expression) {
    UIInternals.JavaScriptInvocation invocation = new UIInternals.JavaScriptInvocation("return " + expression);
    PendingJavaScriptInvocation pending = new PendingJavaScriptInvocation(node, invocation);
    node.runWhenAttached((ui) -> {
      ui.getInternals().getStateTree().beforeClientResponse(node, (context) -> {
        if (!pending.isCanceled()) {
          context.getUI().getInternals().addJavaScriptInvocation(pending);
        }
      });
    });
    return pending;
  }
  private PendingJavaScriptInvocation getJavaScriptInvoke(StateNode node, String expression) {
    UIInternals.JavaScriptInvocation invocation = new UIInternals.JavaScriptInvocation(expression);
    PendingJavaScriptInvocation pending = new PendingJavaScriptInvocation(node, invocation);
    node.runWhenAttached((ui) -> {
      ui.getInternals().getStateTree().beforeClientResponse(node, (context) -> {
        if (!pending.isCanceled()) {
          context.getUI().getInternals().addJavaScriptInvocation(pending);
        }
      });
    });
    return pending;
  }

  public void addTextChangeListener() {
    getJavaScriptInvoke(this.getElement().getNode(),
    "var elem = document.getElementById('text-editor');\n" +
            "\n" +
            "async function waitForTiny() {\n" +
            "    while (typeof tinymce !== \"object\") {\n" +
            "       await sleep(1000); \n" +
            "    }\n" +
            "    return tinymce;\n" +
            "}\n" +
            "\n" +
            "function setupEditor(editor) {\n" +
            "    let observer = new MutationObserver(mutations  => {\n" +
            "        elem.dispatchEvent(new Event('Change'));\n" +
            "    });\n" +
            "    observer.observe(editor, {\n" +
            "    childList: true, // наблюдать за непосредственными детьми\n" +
            "    subtree: true, // и более глубокими потомками\n" +
            "    characterDataOldValue: true // передавать старое значение в колбэк\n" +
            "    });\n" +
            "}\n" +
            "\n" +
            "waitForTiny().then(()=>{\n" +
            "    setupEditor(elem.shadowRoot.getElementById('mce_0_ifr').contentDocument.body);\n" +
            elem + ".shadowRoot.getElementById('mce_0_ifr').contentDocument.body.innerHTML =  '" + innerHTML +  "';"+
            "});\n" +
            "\n" +
            "async function sleep(ms) {\n" +
            "    return new Promise(resolve => setTimeout(resolve, ms));\n" +
            "}\n" +
            "\n"
    );

    getElement().addEventListener("Change", l -> {
      getJavaScriptReturn(this.getElement().getNode(),
              elem +
                      "._editor.getContent()")
              .then(String.class, this::setValue);
    });
  }

  public void setStrValue(String strValue) {
    if (id != null)
      getJavaScriptInvoke(this.getElement().getNode(),
              elem + ".shadowRoot.getElementById('mce_0_ifr').contentDocument.body.innerHTML =  '" + strValue +  "';"
      );
  }
  //TODO: value puts to top sometimes
  public void appendStrValue(String strValue) {
    if (getValue() == null)
      return;
    if (id != null)
      getJavaScriptInvoke(this.getElement().getNode(),
              "tinymce.activeEditor.selection.setContent('" +"<p>" + strValue + "</p>" + "');"
      );
  }


}