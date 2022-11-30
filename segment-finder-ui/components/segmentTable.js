import SegmentRow from "./segmentRow";

const SegmentTable = ({segments}) => {
if(segments.length === 0){
    return (<div></div>);
} else {
return (<table className="table table-hover">
                <thead>
                    <tr>
                        <th onClick={() => console.log("name clicked")}>Name</th>
                        <th>QoM Watts</th>
                        <th>KoM Watts</th>
                        <th>Distance</th>
                        <th>Av Grade</th>
                        <th>Max Grade</th>
                        <th>Elevation Low</th>
                        <th>Elevation High</th>
                        <th>QoM</th>
                        <th>KoM</th>
                        <th>Athlete Count</th>
                        <th>Effort Count</th>
                        <th>Climb Cat</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Country</th>
                    </tr>
                </thead>
                
                <tbody>
                    {segments.map(s => <SegmentRow key={s.id} segment={s}/>)}
                </tbody>

            </table>);
};
}

export default SegmentTable;