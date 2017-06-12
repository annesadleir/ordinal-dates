## English Ordinal Date Formatter
As `java.time.format.DateTimeFormatter`, but will format 'ddd' with an ordinal, e.g. '2nd', '11th' or '23rd'

* only does English ordinals
* only does formatting, not parsing
* created with the static method `ofPattern(String pattern)` as per `DateTimeFormatter`
* formats with the method `format(TemporalAccessor temporalAccessor)` as per `DateTimeFormatter`
* if there's no 'ddd' in the pattern it just passes on to the `DateTimeFormatter`
* treats 'dddd' as 'ddd'+'d', etc, and assumes only one 'ddd' per pattern
* the way it works was heavily inspired by [this Scala/joda code](http://higher-state.blogspot.co.uk/2013/04/extended-joda-datetime-formatter-to.html)

#### tested with generative/property-based testing
as an experiment