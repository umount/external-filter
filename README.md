nutch-element-selector
======================

Nutch 2.3 plugin for whitelisting/blacklisting specific HTML elements.

Usage
======================

To filter HTML elements before parsing, add the following to your nutch-site.xml

```xml
<property>
    <name>parser.filter.blacklist</name>
    <value>footer,div.footer</value>
    <description>
        A comma-delimited list of css like tags to identify the elements which should
        NOT be parsed. Use this to tell the HTML parser to ignore the given elements, e.g. site navigation.
        It is allowed to only specify the element type (required), and optional its class name ('.')
        or ID ('#'). More complex expressions will not be parsed.
        Valid examples: div.header,span,p#test,div#main,ul,div.footercol
        Invalid expressions: div#head#part1,#footer,.inner#post
        Note that the elements and their children will be silently ignored by the parser,
        so verify the indexed content with Luke to confirm results.
        Use either 'parser.html.selector.blacklist' or 'parser.html.selector.whitelist', but not both of them at once. If so,
        only the whitelist is used.
    </description>
</property>
```

or, for a whitelist, replace parser.filter.blacklist with parser.filter.whitelist.

To protect certain pages from filtering, add the following:

```xml
<property>
    <name>parser.filter.protected_urls</name>
    <value>http://www.example.com/home</value>
    <description>Comma separated list of URLs for pages that should be excluded from element filtering</description>
</property>
```

By default, the filtered content will replace the original. If instead you want to store the filtered content to a new filed (thus keeping the original, unfiltered content as well), define the new field as follows:

```xml
<property>
    <name>parser.filter.storage_field</name>
    <value>filtered_content</value>
    <description>The name of the document field where the filtered content should be stored</description>
</property>
```

Also, define a corresponding additional field for your storage if necessary (e.g. add a new column to your RDBMS schema) and add an additional field definition to your Solr schema if you're using Solr.

To enable the plugin, override the default list by adding the following (notice the addition of `element-selector`):

```xml
<property>
    <name>plugin.includes</name>
    <value>protocol-http|urlfilter-regex|parse-(html|tika)|element-filter|index-(basic|anchor)|urlnormalizer-(pass|regex|basic)|scoring-opic</value>
    <description>
        Regular expression naming plugin directory names to
        include.  Any plugin not matching this expression is excluded.
        In any case you need at least include the nutch-extensionpoints plugin. By
        default Nutch includes crawling just HTML and plain text via HTTP,
        and basic indexing and search plugins. In order to use HTTPS please enable 
        protocol-httpclient, but be aware of possible intermittent problems with the 
        underlying commons-httpclient library.
    </description>
</property>
```

Unless you're overriding the storage field, it is important to include `element-filter` _before_ `index-(basic|anchor)`!
