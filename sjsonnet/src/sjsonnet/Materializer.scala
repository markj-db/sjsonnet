package sjsonnet
import ujson.Js
object Materializer {
  def apply(v: Value): Js = v match{
    case Value.True => Js.True
    case Value.False => Js.False
    case Value.Null => Js.Null
    case Value.Num(n) => Js.Num(n)
    case Value.Str(s) => Js.Str(s)
    case Value.Arr(xs) => Js.Arr.from(xs.map(x => apply(x.calc)))
    case obj: Value.Obj =>
      Js.Obj.from(
        for {
          (k, hidden) <- obj.getVisibleKeys().toSeq.sortBy(_._1)
          if !hidden
        }yield k -> apply(obj.value(k).calc)
      )
  }
}
